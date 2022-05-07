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
                function () {
                    $scope.centerData = 'success';
                }
            ).catch(function (data, status, header, config) {
                $scope.centerData = 'failed';
                console.log(data, status);
            });
            $timeout(function (){
                $scope.centerData = '';
            }, 3000);
        } else if ($scope.select === 'enterprise') {
            $http({
                method: 'post',
                url: 'api/database/enterprise',
                data: $scope.enterprise
            }).then(
                function (data, status, header, config) {
                    $scope.enterpriseData = 'success';
                }
            ).catch(function (data, status, header, config) {
                $scope.enterpriseData = 'failed';
                console.log(data, status);
            });
            $timeout(function (){
                $scope.enterpriseData = '';
            }, 3000);
        } else if ($scope.select === 'model') {
            $http({
                method: 'post',
                url: 'api/database/model',
                data: $scope.model
            }).then(
                function (data, status, header, config) {
                    $scope.modelData = 'success';
                }
            ).catch(function (data, status, header, config) {
                $scope.modelData = 'failed';
                console.log(data, status);
            });
            $timeout(function (){
                $scope.modelData = '';
            }, 3000);
        } else if ($scope.select === 'order') {
            $http({
                method: 'post',
                url: 'api/database/order',
                data: $scope.order
            }).then(
                function (data, status, header, config) {
                    $scope.orderData = 'success';
                }
            ).catch(function (data, status, header, config) {
                $scope.orderData = 'failed';
                console.log(data, status);
            });
            $timeout(function (){
                $scope.orderData = '';
            }, 3000);
        } else if ($scope.select === 'staff') {
            $http({
                method: 'post',
                url: 'api/database/staff',
                data: $scope.staff
            }).then(
                function (data, status, header, config) {
                    $scope.staffData = 'success';
                }
            ).catch(function (data, status, header, config) {
                $scope.staffData = 'failed';
                console.log(data, status);
            });
            $timeout(function (){
                $scope.staffData = '';
            }, 3000);
        }
    }
})
