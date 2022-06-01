import csv
import random

data = []
with open('data/in_stoke_test.csv', 'r') as f:
    head = f.readline()
    while True:
        line = f.readline()
        if not line:
            break
        line = line.split(',', 1)
        data.append(line)

with open('data/in_stoke_big.csv', 'w') as f:
    f.write('id,supply_center,product_model,supply_staff,date,purchase_price,quantity\n')
    for i in range(1, 1000001):
        f.write(str(i) + ',' + random.choice(data)[1])
