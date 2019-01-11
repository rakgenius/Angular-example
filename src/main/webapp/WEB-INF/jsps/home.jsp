<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
<head>
<title>Idexx Animana</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.6.0/angular.min.js"></script>
<script src="/js/angular.js"></script>
<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" />
</head>
<body>
	<div class="container" ng-app="app">
		<h1>Animana search</h1>
 		
 		<div class="row">
			<div ng-controller="getlimitController" class="col-md-3">
				<h3>Limit query result</h3>
 				<input type="text" class="form-control" style="width: 150px;" ng-model="limit" /><br />
				<button ng-click="getLimit()">Set</button>
				<div ng-show="showLimit">
					
				</div>
				<p>{{getResultMessage}}</p>
			</div>
		</div>
		
		<div class="row">
			<div ng-controller="getalbumsController" class="col-md-3">
				<h3>Albums</h3>
 				<input type="text" class="form-control" style="width: 150px;" ng-model="albumName" /><br />
				<button ng-click="getAllAlbums()">Get Albums</button>
				<div ng-show="showAllAlbums">
					<ul class="list-group">
						<li ng-repeat="album in allalbums.data"><h4 class="list-group-item">
								<strong>Album {{$index}}</strong><br />
								Title: {{album.title}}<br />
								Artist: {{album.name}}<br />
								Type: {{album.type}}
						</h4></li>
					</ul>
				</div>
				<p>{{getResultMessage}}</p>
			</div>
 
			<div ng-controller="getbooksController" class="col-md-3">
				<h3>Books</h3>
 
				<input type="text" class="form-control" style="width: 150px;" ng-model="bookName" /><br />
				<button ng-click="getAllBooks()">Get Books</button>
 
				<div ng-show="showAllBooks">
					<ul class="list-group">
						<li ng-repeat="book in allbooks.data"><h4	class="list-group-item">
								<strong>Books {{$index}}</strong><br />
								Title: {{book.title}}<br />
								Author: {{book.name}}<br />
								Type: {{book.type}}
						</h4></li>
					</ul>
				</div>
				<p>{{getResultMessage}}</p>
			</div>
	
 
			<div ng-controller="getitemsbynameController" class="col-md-4">
				<h3>Search All</h3>
 
				<input type="text" class="form-control" style="width: 150px;" ng-model="itemName" /><br />
				<button ng-click="getItemsByName()">Get Items</button>
 
				<div ng-show="showItemsByName">
 
					<ul class="list-group">
						<li ng-repeat="item in allitemsbyname.data"><h4	class="list-group-item">
								<strong>Item {{$index}}</strong><br />
								Title: {{item.title}}<br />
								Name: {{item.name}}<br />
								Type: {{item.type}}
						</h4></li>
					</ul>
				</div>
				<p>{{getResultMessage}}</p>
			</div>
 
		</div>
	</div>
</body>
</html>