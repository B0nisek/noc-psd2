angular.module('psdAccountsComponent',[]).component('psdAccounts', {
	bindings:{
		psdAccounts: '<',
		tfDebug: '<',
		ngHide: '<'
	},
	templateUrl: 'tribefire-js-components/psd-accounts/psd-accounts.html',
	controllerAs: 'ctrl',
	controller: function(){
		var self = this;

		self.hideAllTransactions = function(){
			for (var i = 0; i < self.psdAccounts.length; i++) {
				self.psdAccounts[i].transActive = false;
			}
		};

		self.init = function(){
			if(self.tfDebug)console.log(self.psdAccounts);
		};

		self.log = function(){
			if(self.tfDebug)console.log(this);
		};

		self.showTransactions = function(account){
			self.hideAllTransactions();
			account.transActive = true;
		};

		self.$onChanges = function(changesObj){
			if(changesObj.ngHide){
				if(changesObj.ngHide.currentvalue == true){
					self.init();
				}
			}
			self.log();
		}
	}
});