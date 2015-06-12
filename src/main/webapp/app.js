(function() {
    var app = angular.module('Song', []);
    app.controller("SongController", function () {
        this.songId = 0;
        this.selectClick= function(value){
            console.log(value);
            this.songId=value;
        };
        this.isClicked = function(value){
            console.log(value);
            return this.songId === value;
        }
    });
})();