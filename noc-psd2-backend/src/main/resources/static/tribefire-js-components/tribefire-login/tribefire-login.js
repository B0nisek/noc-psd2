(function(angular) {
  'use strict';

angular.module('tfTableTestApp').component('tfLogin', {
  bindings: { 
    onLogin: "=",
    autoLogin: "<"
  },
  templateUrl: 'tribefire-js-components/tribefire-login/tribefire-login.html',
  controller: ("tfTableCtrl",function($scope, $http){

    this.$onInit = function(){
      $scope.autoLogin = this.autoLogin;
    };

    var onLogin = this.onLogin;

    $scope.username = '';
    $scope.password = '';
    $scope.logginError = false;

    $scope.doLogin = function(){
        $http({
            method: 'POST',
            url: 'http://localhost:8080/psd/login',
            data: {
                username: $scope.username,
                password: $scope.password
            }
        }).then(function successCallback(response) {
            onLogin(response);
            $scope.logginError = false;
            $scope.username = '';
            $scope.password = '';
        }, function errorCallback(response) {
            $scope.logginError = true;
            console.log(response);
        });
    };
})
});
})(window.angular);