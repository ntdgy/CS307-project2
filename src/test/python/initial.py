import psycopg2
import sql
import pandas as pd

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





drop_tables()
create_tables()
insert_into_center()
insert_into_enterprise()
insert_into_model()
insert_into_staff()
