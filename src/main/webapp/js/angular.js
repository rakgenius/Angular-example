var app = angular.module('app', []);
 

app.controller('getalbumsController', function($scope, $http, $location) {
	
	$scope.showAllAlbums = false;
 
	$scope.getAllAlbums = function() {
		var url = $location.absUrl() + "findalbums";
 
		var config = {
				headers : {	'Content-Type' : 'application/json;charset=utf-8;' },
				
				params: { 'name' : $scope.albumName }
		}
 
		$http.get(url, config).then(function(response) {
 
			if (response.data.status == "Done") {
				$scope.allalbums = response.data;
				$scope.showAllAlbums = true;
				$scope.getResultMessage = "";
			} else {
				$scope.getResultMessage = "No results found!";
			}
 
		}, function(response) {
			$scope.getResultMessage = "Fail!";
		});
 
	}
});
 
app.controller('getbooksController', function($scope, $http, $location) {
	
	$scope.showAllBooks = false;
	
	$scope.getAllBooks = function() {
		var url = $location.absUrl() + "findbooks";
 
		var config = {
				headers : {	'Content-Type' : 'application/json;charset=utf-8;' },
				
				params: { 'name' : $scope.bookName }
		}
 
		$http.get(url, config).then(function(response) {
 
			if (response.data.status == "Done") {
				$scope.allbooks = response.data;
				$scope.showAllBooks = true;
				$scope.getResultMessage = "";
			} else {
				$scope.getResultMessage = "No results found!";
			}
 
		}, function(response) {
			$scope.getResultMessage = "Fail!";
		});
 
	}
});
 
app.controller('getitemsbynameController', function($scope, $http, $location) {
	
	$scope.showItemsByName = false;
	
	$scope.getItemsByName = function() {
		var url = $location.absUrl() + "findall";
 
		var config = {
			headers : {	'Content-Type' : 'application/json;charset=utf-8;' },
		
			params: { 'name' : $scope.itemName }
		}
 
		$http.get(url, config).then(function(response) {
 
			if (response.data.status == "Done") {
				$scope.allitemsbyname = response.data;
				$scope.showItemsByName = true;
				$scope.getResultMessage = "";
			} else {
				$scope.getResultMessage = "No results found!";
			}
 
		}, function(response) {
			$scope.getResultMessage = "Fail!";
		});
 
	}
});

app.controller('getlimitController', function($scope, $http, $location) {
	 
	$scope.showLimit = false;
	
	$scope.getLimit = function() {
		var url = $location.absUrl() + "limitquery";
 
		var config = {
				headers : {	'Content-Type' : 'application/json;charset=utf-8;' },
				
				params: { 'limit' : $scope.limit }
		}
 
		$http.get(url, config).then(function(response) {
 
			if (response.data.status == "Done") {
				$scope.allLimit = response.data;
				$scope.showLimit = true;
				$scope.getResultMessage = "";
			} else {
				$scope.getResultMessage = "Unable to set the limit";
			}
 
		}, function(response) {
			$scope.getResultMessage = "Fail!";
		});
 
	}
});

