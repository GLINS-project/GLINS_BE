import json
import sys
import pymysql
import random
import requests
from itertools import islice
conn = pymysql.connect(host='glins-db.cjf3exfrqaiy.ap-northeast-2.rds.amazonaws.com',user='admin',password='12345678',db='glins',charset='utf8')

cur = conn.cursor()

## The code for taking all informations from DB ##
cur.execute("SELECT * FROM place")
# cur.execute("INSERT INTO wishlist VALUE('4','6','맛집','10',NULL,NULL)")

## 들어올 입력, 현재는 place_id로 넣어줘야 함 ## 
# want_num = int(input())

## Setting up the variables for this code
result = cur.fetchall()
info_dir = dict() # dictionary 설정을 위한 기본 값 설정 추후에 변경 예정
info_dir_vector_category = dict() # 기존의 category 정보들을 vector 값으로 변경한 dictionary
info_dir_vector_hashtag = dict() # 기존의 hashtag 정보들을 vector 값으로 변경한 dictionary
info_dir_name_to_placeid = dict() # place_id와 가게 이름의 맵핑 정보를 저장하는 dictionary
compare_dir_vector_both = dict() # 
result_list = list() # 결과로 나온 가게 이름들을 저장하는 리스트
result_dict = dict() # 결과 리스트 -> 결과 딕셔너리


#### Setting up the function for this code ####

## 두 리스트의 같은 위치에 1로 되어져 있으면 카운팅 해주는 함수 ## 
def count_matching_elements(list1, list2):
    count = 0
    for i in range(min(len(list1), len(list2))):  # 두 리스트 중 더 짧은 리스트의 길이만큼 반복
        if list1[i] == list2[i] and (list1[i] == 1):  # 같은 위치의 요소가 같은 경우
            count += 1  # 카운트를 1 증가
    return count

## 한 문자열 속에 해당 단어가 있는지 알아내는 함수 ##
def find_word_in_string(s, word):
    if word in s:
        return 1
    else:
        return 0

### 특정 place_id 를 사용자가 클릭했다고 하면, 그 주변에 값들과 유사한 것들을 추천함 ###
## category를 바탕으로 만들어 낸 벡터와 hashtag를 바탕으로 만들어 낸 벡터의 각각의 위치의 원소 값을 더한 후.. ## 
## 클릭한 place의 벡터 값과 모든 벡터 값을 순차적으로 비교 후 결과 리스트(compare_dir_vector_both)를 만들어 냄 ##
def exploit_result(target_num):
    for rotate_key in info_dir.keys():
        compare_dir_vector_both[rotate_key] = count_matching_elements(info_dir_vector_category[target_num],info_dir_vector_category[rotate_key]) + count_matching_elements(info_dir_vector_hashtag[target_num],info_dir_vector_hashtag[rotate_key])


## 데이터 베이스에 있는 정보들 중 category 필드의 모든 정보를 가져요는 코드 ##
#print("<데이터 베이스에 있는 정보들 중 category 필드의 모든 정보>")
for record in result:
    info_dir[record[0]] = record[4]
    info_dir_name_to_placeid[record[0]] = record[3]
    info_dir_vector_category[record[0]] = [0,0,0,0,0,0,0,0,0,0]

## 키워드에 대한 벡터 변경 in category##
## 예시 : 벡터 dir_vector = [카페, 파스타, 고기, 피자, 떡볶이, 라멘, 돈까스(돈가스), 일본, 스시, 맥주] 순으로 값을 배정 ##
for rotate_key in info_dir.keys():
    info_dir[rotate_key] = str(info_dir[rotate_key])
    if find_word_in_string(info_dir[rotate_key],'카페') == 1:
        info_dir_vector_category[rotate_key][0] = 1
    if find_word_in_string(info_dir[rotate_key],'파스타') == 1:
        info_dir_vector_category[rotate_key][1] = 1
    if find_word_in_string(info_dir[rotate_key],'고기') == 1:
        info_dir_vector_category[rotate_key][2] = 1
    if find_word_in_string(info_dir[rotate_key],'피자') == 1:
        info_dir_vector_category[rotate_key][3] = 1
    if find_word_in_string(info_dir[rotate_key],'떡볶이') == 1:
        info_dir_vector_category[rotate_key][4] = 1
    if find_word_in_string(info_dir[rotate_key],'라멘') == 1:
        info_dir_vector_category[rotate_key][5] = 1
    if find_word_in_string(info_dir[rotate_key],'돈까스') == 1:
        info_dir_vector_category[rotate_key][6] = 1
    if find_word_in_string(info_dir[rotate_key],'일본') == 1:
        info_dir_vector_category[rotate_key][7] = 1
    if find_word_in_string(info_dir[rotate_key],'스시') == 1:
        info_dir_vector_category[rotate_key][8] = 1
    if find_word_in_string(info_dir[rotate_key],'맥주') == 1:
        info_dir_vector_category[rotate_key][9] = 1
    # print('place_id = ',end = ' ')
    # print(rotate_key,end = ' ')
    # print('vector = ', end = ' ')
    # print(info_dir_vector_category[rotate_key])

## 데이터 베이스에 있는 정보들 중 hashtag 필드의 모든 정보를 가져요는 코드 ##
#print("<데이터 베이스에 있는 정보들 중 hashtag 필드의 모든 정보>")
for record in result:
    info_dir[record[0]] = record[5]
    info_dir_vector_hashtag[record[0]] = [0,0,0,0,0,0,0,0,0,0]

## 키워드에 대한 벡터 변경 in category##
## 예시 : 벡터 dir_vector = [데이트, 단체석, 주차, 배달, 캐주얼, 술, 이쁜, 간식, 조용한, 가성비] 순으로 값을 배정 ##
for rotate_key in info_dir.keys():
    info_dir[rotate_key] = str(info_dir[rotate_key])
    if find_word_in_string(info_dir[rotate_key],'데이트') == 1:
        info_dir_vector_hashtag[rotate_key][0] = 1
    if find_word_in_string(info_dir[rotate_key],'단체석') == 1:
        info_dir_vector_hashtag[rotate_key][1] = 1
    if find_word_in_string(info_dir[rotate_key],'주차') == 1:
        info_dir_vector_hashtag[rotate_key][2] = 1
    if find_word_in_string(info_dir[rotate_key],'배달') == 1:
        info_dir_vector_hashtag[rotate_key][3] = 1
    if find_word_in_string(info_dir[rotate_key],'캐주얼') == 1:
        info_dir_vector_hashtag[rotate_key][4] = 1
    if find_word_in_string(info_dir[rotate_key],'술') == 1:
        info_dir_vector_hashtag[rotate_key][5] = 1
    if find_word_in_string(info_dir[rotate_key],'이쁜') == 1:
        info_dir_vector_hashtag[rotate_key][6] = 1
    if find_word_in_string(info_dir[rotate_key],'간식') == 1:
        info_dir_vector_hashtag[rotate_key][7] = 1
    if find_word_in_string(info_dir[rotate_key],'조용한') == 1:
        info_dir_vector_hashtag[rotate_key][8] = 1
    if find_word_in_string(info_dir[rotate_key],'가성비') == 1:
        info_dir_vector_hashtag[rotate_key][9] = 1
#     # print('place_id = ',end = ' ')
#     # print(rotate_key,end = ' ')
#     # print('vector = ', end = ' ')
#     # print(info_dir_vector_hashtag[rotate_key])

## exploit_result 함수 실행 ##
arg = int(sys.argv[1])
exploit_result(arg)


## 결과 리스트에서 최고 값과 같은 값들을 출력해 냄 ## 
for rotate_key in info_dir.keys():
    if max(compare_dir_vector_both.values()) == compare_dir_vector_both[rotate_key]:
        ## 결과 data를 JSON으로 바꾸기 위한 list -> dict 과정 ##
        result_dict[rotate_key] = info_dir_name_to_placeid[rotate_key]


# # ## list로만 뽑아내는 과정 ##
# values_list = list(result_dict.values())
# random.shuffle(values_list)
# print(result_list[:5])

## 결과 dict에 있는 값들 shuffle ##
result_list = list(result_dict.items())
random.shuffle(result_list)
result_dict = dict(result_list)
for key,value in islice(result_dict.items(), 5):
    print(key, end=' ')

# ## 결과 dict를 JSON으로 변경 ##
# result_dict = islice(result_dict.items(), 5)
# result_dict = dict(result_dict)
# with open("data.json","w") as f:
#     json.dump(result_dict,f,ensure_ascii=False)

# print(result_dict)

# 데이터 베이스 연결 종료 # 
conn.close()