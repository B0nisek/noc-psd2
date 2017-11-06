var app = angular.module('multibankApp', ['ngMaterial', 'ngMessages', 'psdAddAccountComponent','psdAccountsComponent', 'md.data.table'])
	.config(function($mdThemingProvider) {
	    $mdThemingProvider.definePalette('amazingPaletteName', {
            '50': '80ff00',
            '100': '80ff00',
            '200': '80ff00',
            '300': '80ff00',
            '400': 'ffc766',
            '500': 'ffb433',
            '600': 'ffa300',
            '700': 'd32f2f',
            '800': 'c62828',
            '900': 'b71c1c',
            'A100': 'ff8a80',
            'A200': 'ff5252',
            'A400': 'ff1744',
            'A700': 'd50000',
	        'contrastDefaultColor': 'light',    // whether, by default, text (contrast)
	                                            // on this palette should be dark or light

	        'contrastDarkColors': ['50', '100', //hues which contrast should be 'dark' by default
	         '200', '300', '400', 'A100'],
	        'contrastLightColors': undefined    // could also specify this if default was 'dark'
	      });

	  $mdThemingProvider.theme('default')
	    .primaryPalette('amazingPaletteName');
	    //.accentPalette('orange');
	});

app.controller('mainController',function($scope, $http){
    $scope.loggedIn = false;
    $scope.authToken = null;
    $scope.userError = false;
    $scope.addAccountErrorMessage = null;


    $scope.owner = null;

    $scope.loginCallBack = function(loginResult){
        $scope.authToken = loginResult.data.token;
        $scope.loggedIn = true;
        loadBankAccounts();
    };

    $scope.logoutCallback = function(logoutResult){
        $scope.authToken = null;
        $scope.loggedIn = false;
    };

    function loadBankAccounts(){
        $http({
            method: 'GET',
            url: 'http://localhost:8080/psd/acc',
            headers: {
                'auth-token' : '' + $scope.authToken + ''
            }
        }).then(function successCallback(response) {
            $scope.owner = response.data;
            console.log($scope.owner);
        }, function errorCallback(response) {
            console.log("Error when loading accounts");
            console.log(response);
        });
    }

    $scope.addNewAccount = function (account) {
        $http({
            method: 'POST',
            url: 'http://localhost:8080/psd/acc/add',
            headers: {
                'auth-token' : '' + $scope.authToken + ''
            },
            data: {
                iban: account.iban,
                alias: account.alias,
                username: account.username,
                password: account.pwd,
                tan: account.tan
            }
        }).then(function successCallback(response) {
            console.log(response);
            $scope.addAccountErrorMessage = false;
            loadBankAccounts();
        }, function errorCallback(response) {
            console.log(response);
            $scope.addAccountErrorMessage = response.data.message;
        });
    };
});