var app = angular.module('manage', []);
app.controller('manager', function ($scope, $http, $timeout) {
    $scope.formData = {};
    $scope.center = {};
    $scope.enterprise = {};
    $scope.model = {};
    $scope.order = {};
    $scope.staff = {};
    $scope.mysubmit = function () {
        if ($scope.select === 'center') {
            $http({
                method: 'post',
                url: 'api/database/center',
                data: $scope.center
            }).then(
                function (data, status, header, config) {
                    $scope.centerData = data.data.result;
                }
            ).catch(function (data, status, header, config) {
                $scope.centerFail = data.data.result;
                console.log(data, status);
            });
            $timeout(function (){
                $scope.centerData = '';
                $scope.centerFail = '';
            }, 3000);
        } else if ($scope.select === 'enterprise') {
            $http({
                method: 'post',
                url: 'api/database/enterprise',
                data: $scope.enterprise
            }).then(
                function (data, status, header, config) {
                    $scope.enterpriseData = data.data.result;
                }
            ).catch(function (data, status, header, config) {
                $scope.enterpriseFail = data.data.result;
                console.log(data, status);
            });
            $timeout(function (){
                $scope.enterpriseData = '';
                $scope.enterpriseFail = '';
            }, 3000);
        } else if ($scope.select === 'model') {
            $http({
                method: 'post',
                url: 'api/database/model',
                data: $scope.model
            }).then(
                function (data, status, header, config) {
                    $scope.modelData = data.data.result;
                }
            ).catch(function (data, status, header, config) {
                $scope.modelFail = data.data.result;
                console.log(data, status);
            });
            $timeout(function (){
                $scope.modelData = '';
                $scope.modelFail = '';
            }, 3000);
        } else if ($scope.select === 'order') {
            $http({
                method: 'post',
                url: 'api/database/order',
                data: $scope.order
            }).then(
                function (data, status, header, config) {
                    $scope.orderData = data.data.result;
                }
            ).catch(function (data, status, header, config) {
                $scope.orderFail = data.data.result;
                console.log(data, status);
            });
            $timeout(function (){
                $scope.orderData = '';
                $scope.orderFail = '';
            }, 3000);
        } else if ($scope.select === 'staff') {
            $http({
                method: 'post',
                url: 'api/database/staff',
                data: $scope.staff
            }).then(
                function (data, status, header, config) {
                    $scope.staffData = data.data.result;
                }
            ).catch(function (data, status, header, config) {
                $scope.staffFail = data.data.result;
                console.log(data, status);
            });
            $timeout(function (){
                $scope.staffData = '';
                $scope.staffFail = '';
            }, 3000);
        }
    }
})
