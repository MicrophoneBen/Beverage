
/*global define */

'use strict';

define(['angular', './navigation.module'], function (angular, module) {
    module.controller('welcome', ['$scope', '$http', '$location',
        function ($scope, $http, $location) {
            var vm = this;
            var numberOfSlides = 10;
            vm.myInterval = 5000;
            vm.slides = [];

            activate();

//Photo by Adam Wilson on Unsplash

            function activate() {
                vm.slides.push({
                    image: 'images/adam-wilson-138927.jpg',
                    text: 'Photo by Adam Wilson on Unsplash',
                    id: 0
                });
                
                vm.slides.push({
                    image: 'images/fabio-santaniello-bruun-193920.jpg',
                    text: 'Photo by Fabio Santaniello Bruun on Unsplash',
                    id: 1
                });
                
                vm.slides.push({
                    image: 'images/james-sutton-195612.jpg',
                    text: 'Photo by James Sutton on Unsplash',
                    id: 2
                });
                
                vm.slides.push({
                    image: 'images/maeghan-smulders-240308.jpg',
                    text: 'Photo by Maeghan Smulders on Unsplash',
                    id: 3
                });    
                
                vm.slides.push({
                    image: 'images/mattias-diesel-281546.jpg',
                    text: 'Photo by Mattias Diesel on Unsplash',
                    id: 4
                });   
                
                 vm.slides.push({
                    image: 'images/yulia-combat-33287.jpg',
                    text: 'Photo by Yulia Combat on Unsplash',
                    id: 5
                }); 
                
                
                

            }


        }]);

});



