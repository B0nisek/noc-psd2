(function(angular) {
  'use strict';
angular.module('multibankApp').component('header', {
    bindings: {
        onLogout: "=",
        authToken: "<"
    },

  templateUrl: 'components/header/header.html',
  controller: ("headerController", function($scope, $http){

      var onLogout = this.onLogout;
      var authToken = this.authToken;

      $scope.doLogout = function(){
          $http({
              method: 'GET',
              url: 'http://localhost:8080/psd/logout',
              headers: {
                  'auth-token' : '\'' + authToken + '\''
              }
          }).then(function successCallback(response) {
              onLogout(response);
          }, function errorCallback(response) {
              console.log(response);
          });
      };

  })
});
})(window.angular);