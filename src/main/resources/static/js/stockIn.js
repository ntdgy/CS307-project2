var app = angular.module('stock', []);
var table;
app.controller('stockIn', function ($scope, $http) {
    $scope.mysubmit = function () {
        console.log($scope.order)
        console.log($scope.order.date)
        $http({
            method: 'post',
            url: 'api/database/stockIn',
            data: $scope.order
        }).then(
            function (data, status, header, config) {
                $scope.return = data.data;
            }
        ).catch(function (data, status, header, config) {
            console.log(data, status);
        });
    }
})
