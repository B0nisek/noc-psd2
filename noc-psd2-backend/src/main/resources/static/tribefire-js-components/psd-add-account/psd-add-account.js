angular.module('psdAddAccountComponent',[]).component('psdAddAccount', {
	bindings:{
		addNewAccountCallback: '<',
		stageNames: '@?',
		ngHide: '<'
	},
	templateUrl: 'tribefire-js-components/psd-add-account/psd-add-account.html',
	controllerAs: 'vm',
	controller: function($location,$anchorScroll,$timeout){
		var self = this;
        self.stages = self.stageNames.split(",");
        self.isActive = false;
        self.buttonText = "Add Account";
        self.selectedIndex = 0;
        self.maxIndex = 2;
        self.account = {};


		self.init = function(){
			if(self.tfDebug)console.log("initialise component here");
		};

		self.addAccount = function () {
			if(!self.isActive) {
				self.buttonText = "Cancel";
                self.isActive = true;
                $timeout(function(){
                	//scroll to addAccount div
					$location.hash('addAccount');
					$anchorScroll();
				}, 50);
            } else {
				self.buttonText = "Add Account";
				self.account = {};
				self.isActive = false;
			}
        };

        self.nextTab = function () {
			var index = (self.selectedIndex == self.maxIndex) ? 0 : self.selectedIndex + 1;
			self.selectedIndex = index;
        };

        self.previousTab = function () {
			var index = (self.selectedIndex == 0) ? self.maxIndex : self.selectedIndex - 1;
			self.selectedIndex = index;
        };
        
        self.submit = function () {
			self.addNewAccountCallback(self.account);
			self.selectedIndex = 0;
			self.addAccount();
        };

		self.$onChanges = function(changesObj){
			if(changesObj.ngHide){
				if(changesObj.ngHide.currentvalue == true){
					self.init();
				}
			}
		}
	}
});