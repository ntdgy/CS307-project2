# CS307 Project 2

**Contributor**:

- 戴郭轶 12011211
- 周凡卜 12012519

# Task 1: Overall introduction 

We use springboot as our blackened to connect to the database and offer http/https serve. The optimization of  `Thread Pool` and `DataSource Connection Pool` enables us to serve over `70k` requests in `one minite`. Also,  we provides `User-friendly interface` to react with our users and `perfect authority management system` to control the permission of each user.

# Task 2: API Design

For all data, we just receive a parameter from `dto`, so all the parameters can be null for any opration unless required.

### 1. Basic Api

We support all basic table select, update, insert and delete operations. We set a while list for the parameters in case of SQL injection.



