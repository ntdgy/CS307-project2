import psycopg2
import sql
import pandas as pd
import requests
import json

pgsql = psycopg2.connect(
    host="42.194.178.20",
    port="5432",
    database="final",
    user="final",
    password='CS307final',
)
pgsql.autocommit = True
cursor = pgsql.cursor()


def drop_tables():
    cursor.execute(sql.drop_table)


def create_tables():
    cursor.execute(sql.create_table)


def insert_into_center():
    with open('data/center.csv', 'r') as f:
        f.readline()
        cursor.copy_from(f, 'center', sep='|')


def insert_into_enterprise():
    file = pd.read_csv('data/enterprise.csv')
    strings = []
    for index, row in file.iterrows():
        strings.append([row[0], row[1], row[2], row[3], row[4], row[5]])
    cursor.executemany(sql.insert_into_enterprise, strings)


def insert_into_model():
    with open('data/model.csv', 'r') as f:
        f.readline()
        cursor.copy_from(f, 'model', sep=',')


def insert_into_staff():
    file = pd.read_csv('data/staff.csv')
    file['type'].replace('Director', '0', inplace=True)
    file['type'].replace('Supply Staff', '1', inplace=True)
    file['type'].replace('Contracts Manager', '2', inplace=True)
    file['type'].replace('Salesman', '3', inplace=True)
    strings = []
    for index, row in file.iterrows():
        strings.append([row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7]])
    cursor.executemany(sql.insert_into_staff, strings)


def stock_in():
    url = "http://localhost:8080/api/database/stockIn"
    headers = {
        'Content-Type': "application/json",
        'Accept': "application/json",
        'Cookie': 'JSESSIONID=FA4DAEBCC9F4A6EEA31927BB8DC857F9'
    }
    test = {
        # id,supply_center,product_model,supply_staff,date,purchase_price,quantity
        'id': 1,
        'supply_center': '',
        'product_model': '',
        'supply_staff': '',
        'date': '',
        'purchase_price': 1,
        'quantity': 1
    }
    with open('data/task1_in_stoke_test_data_publish.csv', 'r') as f:
        head = f.readline()
        while True:
            line = f.readline()
            if not line:
                break
            line = line.split(',')
            test['id'] = line[0]
            test['supplycenter'] = line[1]
            test['productmodel'] = line[2]
            test['supplystaff'] = line[3]
            test['date'] = line[4].replace('/', '-')
            test['purchaseprice'] = line[5]
            test['quantity'] = line[6].replace('\n', '')
            re = requests.post(url, headers=headers, json=test)
            print(re.text)


def place_prder():
    url = "http://localhost:8080/api/database/placeorder"
    headers = {
        'Content-Type': "application/json",
        'Accept': "application/json",
        'Cookie': 'JSESSIONID=FA4DAEBCC9F4A6EEA31927BB8DC857F9'
    }
    test = {
        # id,supply_center,product_model,supply_staff,date,purchase_price,quantity
        'contract_num': 'CSE0000101',
        'enterprise': 'ENI',
        'productmodel': 'ElectricKettleR3',
        'quantity': 1,
        'contractmanager': '12112115',
        'contractdate': '2022-01-01',
        'estimateddelivery_date': '2022-01-06',
        'lodgementdate': '2022-01-06',
        'salesmannum': '11610016',
        'contracttype': 'Finished'
    }
    with open('data/task2_test_data_publish.csv', 'r') as f:
        head = f.readline()
        while True:
            line = f.readline()
            if not line:
                break
            line = line.split(',')
            test['contract_num'] = line[0]
            test['enterprise'] = line[1]
            test['productmodel'] = line[2]
            test['quantity'] = line[3]
            test['contractmanager'] = line[4]
            test['contractdate'] = line[5].replace('/', '-')
            test['estimateddelivery_date'] = line[6].replace('/', '-')
            test['lodgementdate'] = line[7].replace('/', '-')
            test['salesmannum'] = line[8]
            test['contracttype'] = line[9]
            re = requests.post(url, headers=headers, json=test)
            print(re.text)


# drop_tables()
# create_tables()
# insert_into_center()
# insert_into_enterprise()
# insert_into_model()
# insert_into_staff()
# stock_in()
place_prder()



# url = "http://localhost:8080/api/database/placeorder"
# headers = {
#     'Content-Type': "application/json",
#     'Accept': "application/json",
#     'Cookie': 'JSESSIONID=FA4DAEBCC9F4A6EEA31927BB8DC857F9'
# }
# test = {
#     # contract_num,enterprise,product_model,quantity,contract_manager,contract_date,estimated_delivery_date, lodgement_date,salesman_num,contract_type
#     'contract_num': 'CSE0000101',
#     'enterprise': 'ENI',
#     'productmodel': 'ElectricKettleR3',
#     'quantity': 1,
#     'contractmanager': '12112115',
#     'contractdate': '2022-01-01',
#     'estimateddelivery_date': '2022-01-06',
#     'lodgementdate': '2022-01-06',
#     'salesmannum': '11610016',
#     'contracttype': 'Finished'
# }
# print(json.dumps(test))
# re = requests.post(url, headers=headers, json=test)
# print(re.status_code)
# print(re.text)

