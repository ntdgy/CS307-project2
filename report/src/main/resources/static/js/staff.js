var app = angular.module('staff', []);
app.controller('staff', function ($scope, $http, $timeout) {
    $scope.place = {};
    $scope.update = {};
    $scope.delete = {};
    $scope.mysubmit = function () {
        if ($scope.type === 'place') {
            $http({
                method: 'post',
                url: 'api/database/placeorder',
                data: $scope.place
            }).then(
                function (data, status, header, config) {
                    $scope.orderData = data.data.result;
                }
            ).catch(function (data, status, header, config) {
                $scope.orderData = data.data.result;
                console.log(data, status);
            });
        } else if ($scope.type === 'update') {
            $http({
                method: 'post',
                url: 'api/database/updateOrder',
                data: $scope.update
            }).then(
                function (data, status, header, config) {
                    $scope.orderData = data.data.result;
                }
            ).catch(function (data, status, header, config) {
                $scope.orderData = data.data.result;
                console.log(data, status);
            });
        } else if ($scope.type === 'delete') {
            $http({
                method: 'post',
                url: 'api/database/deleteOrder',
                data: $scope.delete
            }).then(
                function (data, status, header, config) {
                    $scope.orderData = data.data.result;
                }
            ).catch(function (data, status, header, config) {
                $scope.orderData = data.data.result;
                console.log(data, status);
            });
        }
        $timeout(function (){
            $scope.orderData = '';
            $scope.orderData = '';
        }, 3000);
    }
})
