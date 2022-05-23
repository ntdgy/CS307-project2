var app = angular.module('main', []);
var table;
app.controller('tt', function ($scope, $http) {
    $scope.avgData = {
        a: 1
    }
    $scope.staffData = {
        a: 1
    }
    $scope.favoriteData = {
        a: 1
    }
    staff = function () {
        $http({
            method: 'post',
            url: 'api/database/getAllStaffCount'
        }).then(
            function (data, status, header, config) {
                $scope.staffData = data.data;
                console.log(data);
            }
        ).catch(function (data, status, header, config) {
            console.log(data, status);
        });
    };
    contract = function () {
        $http({
            method: 'post',
            url: 'api/database/getContractCount'
        }).then(
            function (data, status, header, config) {
                $scope.contractData = data.data;
                console.log(data);
            }
        ).catch(function (data, status, header, config) {
            console.log(data, status);
        });
    };
    order = function () {
        $http({
            method: 'post',
            url: 'api/database/getOrderCount'
        }).then(
            function (data, status, header, config) {
                $scope.orderData = data.data;
                console.log(data);
            }
        ).catch(function (data, status, header, config) {
            console.log(data, status);
        });
    };
    never = function () {
        $http({
            method: 'post',
            url: 'api/database/getNeverSoldProductCount'
        }).then(
            function (data, status, header, config) {
                $scope.neverData = data.data;
                console.log(data);
            }
        ).catch(function (data, status, header, config) {
            console.log(data, status);
        });
    };
    favorite = function () {
        $http({
            method: 'post',
            url: 'api/database/getFavoriteProductModel'
        }).then(
            function (data, status, header, config) {
                $scope.favoriteData = data.data.result;
                console.log(data);
                if($scope.favoriteData.length === 0){
                    console.log("????")
                    $scope.favoriteData = {
                        a: {
                            quantity: 'NaN'
                        }
                    }
                }
            }
        ).catch(function (data, status, header, config) {
            console.log(data, status);
        });
    };
    avg = function () {
        $http({
            method: 'post',
            url: 'api/database/getAvgStockByCenter'
        }).then(
            function (data, status, header, config) {
                $scope.avgData = data.data.result;
                console.log(data);
                if($scope.avgData.length === 0){
                    console.log("????")
                    $scope.avgData = {
                        a: {
                            round: 'NaN'
                        }
                    }
                }
            }
        ).catch(function (data, status, header, config) {
            console.log(data, status);
        });
    };
    //initialize
    staff();
    contract();
    order();
    never();
    favorite();
    avg();
})
