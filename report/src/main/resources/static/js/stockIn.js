var app = angular.module('stock', []);
var table;
app.controller('stockIn', function ($scope, $http, $timeout) {
    $scope.mysubmit = function () {
        console.log($scope.order)
        console.log($scope.order.date)
        $http({
            method: 'post',
            url: 'api/database/stockIn',
            data: $scope.order
        }).then(
            function (data, status, header, config) {
                $scope.return = 'Success';
            }
        ).catch(function (data, status, header, config) {
            console.log(data, status);
            $scope.returnFail = data.data.result;
        });
        $timeout(function (){
            $scope.return = '';
            $scope.returnFail = '';
        }, 3000);
    }
})
