var app = angular.module('app', []);
var table;
app.controller('test', function ($scope, $http) {
    $scope.formData = {};
    $scope.center = {};
    $scope.enterprise = {};
    $scope.model = {};
    $scope.order = {};
    $scope.staff = {};
    $scope.contract = {};
    $scope.number = {};
    $scope.info = {};
    $scope.tables = [];
    $scope.mysubmit = function () {
        if ($scope.tables.table === 'center') {
            $http({
                method: 'post',
                url: 'api/database/select/center',
                data: $scope.center
            }).then(
                function (data, status, header, config) {
                    $scope.centerData = data.data;
                }
            ).catch(function (data, status, header, config) {
                console.log(data, status);
            });
        } else if ($scope.tables.table === 'enterprise') {
            $http({
                method: 'post',
                url: 'api/database/select/enterprise',
                data: $scope.enterprise
            }).then(
                function (data, status, header, config) {
                    $scope.enterpriseData = data.data;
                }
            ).catch(function (data, status, header, config) {
                console.log(data, status);
            });
        } else if ($scope.tables.table === 'model') {
            $http({
                method: 'post',
                url: 'api/database/select/model',
                data: $scope.model
            }).then(
                function (data, status, header, config) {
                    $scope.modelData = data.data;
                }
            ).catch(function (data, status, header, config) {
                console.log(data, status);
            });
        } else if ($scope.tables.table === 'order') {
            $http({
                method: 'post',
                url: 'api/database/select/order',
                data: $scope.order
            }).then(
                function (data, status, header, config) {
                    $scope.orderData = data.data;
                }
            ).catch(function (data, status, header, config) {
                console.log(data, status);
            });
        } else if ($scope.tables.table === 'staff') {
            $http({
                method: 'post',
                url: 'api/database/select/staff',
                data: $scope.staff
            }).then(
                function (data, status, header, config) {
                    $scope.staffData = data.data;
                }
            ).catch(function (data, status, header, config) {
                console.log(data, status);
            });
        } else if ($scope.tables.table === 'contract') {
            $http({
                method: 'post',
                url: 'api/database/select/contract',
                data: $scope.contract
            }).then(
                function (data, status, header, config) {
                    $scope.contractData = data.data;
                }
            ).catch(function (data, status, header, config) {
                console.log(data, status);
            });
        } else if ($scope.tables.table === 'number') {
            $http({
                method: 'post',
                url: 'api/database/getProductByNumber',
                data: $scope.number
            }).then(
                function (data, status, header, config) {
                    console.log(data, status);
                    $scope.numberData = data.data;
                }
            ).catch(function (data, status, header, config) {
                console.log(data, status);
            });
        } else if ($scope.tables.table === 'info') {
            $http({
                method: 'post',
                url: 'api/database/getContractInfo',
                data: $scope.info
            }).then(
                function (data, status, header, config) {
                    $scope.infoData = data.data;
                    console.log(data, status);
                }
            ).catch(function (data, status, header, config) {
                console.log(data, status);
            });
        }
    }
})
