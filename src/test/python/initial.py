import psycopg2
import sql
import pandas as pd
import requests

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
    for index, row in file.iterrows():
        cursor.execute(sql.insert_into_enterprise, )



def insert_into_model():
    with open('data/model.csv', 'r') as f:
        f.readline()
        cursor.copy_from(f, 'model', sep=',')


def insert_into_staff():
    with open('data/staff.csv', 'r') as f:
        f.readline()
        cursor.copy_from(f, 'staff', sep=',')


def stock_in():
    url = "http://localhost:8080/api/database/stockIn"
    headers = {
        'Content-Type': "application/json",
        'Accept': "application/json",
        'Cookie': 'JSESSIONID=FA4DAEBCC9F4A6EEA31927BB8DC857F9'
    }
    map = {
        'id':1,
        'name':
    }
    with open('data/task1_in_stoke_test_data_publish.csv', 'r') as f:
        head = f.readline()
        head = head.split(',')
        while True:
            line = f.readline()
            if not line:
                break
            line = line.split(',')
            map[head[0]] = line[1]








# drop_tables()
# create_tables()
# insert_into_center()
insert_into_enterprise()
insert_into_model()

