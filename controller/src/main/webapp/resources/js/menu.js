///*
//   Simple JQuery Accordion menu.
//   HTML structure to use:
//
//   <ul id="menu">
//     <li><a href="#">Sub menu heading</a>
//     <ul>
//       <li><a href="http://site.com/">Link</a></li>
//       <li><a href="http://site.com/">Link</a></li>
//       <li><a href="http://site.com/">Link</a></li>
//       ...
//       ...
//     </ul>
//     <li><a href="#">Sub menu heading</a>
//     <ul>
//       <li><a href="http://site.com/">Link</a></li>
//       <li><a href="http://site.com/">Link</a></li>
//       <li><a href="http://site.com/">Link</a></li>
//       ...
//       ...
//     </ul>
//     ...
//     ...
//   </ul>
//
//Copyright 2007 by Marco van Hylckama Vlieg
//
//web: http://www.i-marco.nl/weblog/
//email: marco@i-marco.nl
//
//Free for non-commercial use
//*/
//
//function initMenu() {
//  $('#menu ul').hide();
//  $('#menu ul a').children('.current').parent().show();
//  //$('#menu ul:first').show();
//  $('#menu li a').hover(
//    function() {
//      var checkElement = $(this).next();
//      if((checkElement.is('ul')) && (!checkElement.is($(this))) && (checkElement.is(':visible'))) {
//          checkElement.slideUp('normal');
//        return false;
//        }
//      if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
//        $('#menu ul ul:visible').slideUp('normal');
//        checkElement.slideDown('normal');
//          //$('#menu ul:visible').slideUp('normal');
//          //checkElement.slideDown('normal');
//        return false;
//        }
//      }
//    );
//  }
//$(document).ready(function() {initMenu();});