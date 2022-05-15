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
        'supplycenter': '',
        'productmodel': '',
        'supplystaff': '',
        'date': '',
        'purchase_price': 1,
        'quantity': ''
    }
    with open('data/task1_in_stoke_test_data_publish.csv', 'r') as f:
        head = f.readline()
        while True:
            line = f.readline()
            if not line:
                break
            line = line.replace('"', '').split(',')
            test['id'] = line[0]
            if line[1] == 'Hong Kong':
                test['supplycenter'] = line[1]+', Macao and Taiwan regions of China'
                test['productmodel'] = line[3]
                test['supplystaff'] = line[4]
                test['date'] = line[5].replace('/', '-')
                test['purchase_price'] = line[6]
                test['quantity'] = line[7].replace('\n', '')
            else:
                test['supplycenter'] = line[1]
                test['productmodel'] = line[2]
                test['supplystaff'] = line[3]
                test['date'] = line[4].replace('/', '-')
                test['purchase_price'] = line[5]
                test['quantity'] = line[6].replace('\n', '')
            response = requests.post(url, headers=headers, data=json.dumps(test))
            print(response.text)


def place_prder():
    url = "http://localhost:8080/api/database/placeorder"
    headers = {
        'Content-Type': "application/json",
        'Accept': "application/json",
        'Cookie': 'JSESSIONID=FA4DAEBCC9F4A6EEA31927BB8DC857F9'
    }
    test = {
        # id,supply_center,product_model,supply_staff,date,purchase_price,quantity
        'contractnum': 'CSE0000101',
        'enterprise': 'ENI',
        'productmodel': 'ElectricKettleR3',
        'quantity': 1,
        'contractmanager': '12112115',
        'contractdate': '2022-01-01',
        'estimateddeliverydate': '2022-01-06',
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
            test['contractnum'] = line[0]
            test['enterprise'] = line[1]
            test['productmodel'] = line[2]
            test['quantity'] = line[3]
            test['contractmanager'] = line[4]
            test['contractdate'] = line[5].replace('/', '-')
            test['estimateddeliverydate'] = line[6].replace('/', '-')
            test['lodgementdate'] = line[7].replace('/', '-')
            test['salesmannum'] = line[8]
            test['contracttype'] = line[9]
            re = requests.post(url, headers=headers, json=test)
            print(re.text)


def update_order():
    url = "http://localhost:8080/api/database/updateOrder"
    headers = {
        'Content-Type': "application/json",
        'Accept': "application/json",
        'Cookie': 'JSESSIONID=FA4DAEBCC9F4A6EEA31927BB8DC857F9'
    }
    test = {
        # contract	product_model	salesman	quantity	estimate_delivery_date	lodgement_date
        # CSE0000208	ComputerTerminal43	11110405	1	2022-02-28	2022-02-13
        'contract': 'CSE0000208',
        'productmodel': 'ComputerTerminal43',
        'salesmannum': '11110405',
        'quantity': 1,
        'estimateddeliverydate': '2022-02-28',
        'lodgementdate': '2022-02-13'
    }
    with open('data/task34_update_test_data_publish.tsv', 'r') as f:
        head = f.readline()
        while True:
            line = f.readline()
            if not line:
                break
            line = line.split()
            test['contractnum'] = line[0]
            test['productmodel'] = line[1]
            test['salesman'] = line[2]
            test['quantity'] = line[3]
            test['estimateddeliverydate'] = line[4].replace('/', '-')
            test['lodgementdate'] = line[5].replace('/', '-')
            re = requests.post(url, headers=headers, json=test)
            print(re.status_code)
            print(re.text)


def delete_order():
    url = "http://localhost:8080/api/database/deleteOrder"
    headers = {
        'Content-Type': "application/json",
        'Accept': "application/json",
        'Cookie': 'JSESSIONID=FA4DAEBCC9F4A6EEA31927BB8DC857F9'
    }
    test = {
        # contract	salesman	seq
        # CSE0000103	11611117	1
        'contract': 'CSE0000103',
        'salesman': '11611117',
        'seq': 1
    }
    with open('data/task34_delete_test_data_publish.tsv', 'r') as f:
        head = f.readline()
        while True:
            line = f.readline()
            if not line:
                break
            line = line.split()
            test['contract'] = line[0]
            test['salesman'] = line[1]
            test['seq'] = line[2]
            re = requests.post(url, headers=headers, json=test)
            print(re.status_code)
            print(re.text)




drop_tables()
create_tables()
insert_into_center()
insert_into_enterprise()
insert_into_model()
insert_into_staff()
stock_in()
place_prder()
update_order()
delete_order()


# url = "http://localhost:8080/api/database/deleteOrder"
# headers = {
#     'Content-Type': "application/json",
#     'Accept': "application/json",
#     'Cookie': 'JSESSIONID=FA4DAEBCC9F4A6EEA31927BB8DC857F9'
# }
# test = {
#     # contract	salesman	seq
#     # CSE0000103	11611117	1
#     'contract': 'CSE0000103',
#     'salesman': '11611117',
#     'seq': 1
# }
# print(json.dumps(test))
# re = requests.post(url, headers=headers, json=test)
# print(re.status_code)
# print(re.text)
