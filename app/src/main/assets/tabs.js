// { "framework": "Vue"} 

!function(t){function e(r){if(n[r])return n[r].exports;var s=n[r]={i:r,l:!1,exports:{}};return t[r].call(s.exports,s,s.exports,e),s.l=!0,s.exports}var n={};e.m=t,e.c=n,e.i=function(t){return t},e.d=function(t,n,r){e.o(t,n)||Object.defineProperty(t,n,{configurable:!1,enumerable:!0,get:r})},e.n=function(t){var n=t&&t.__esModule?function(){return t.default}:function(){return t};return e.d(n,"a",n),n},e.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},e.p="",e(e.s=6)}([function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default={data:{sindex:1,jsPath:"index.js"},methods:{changed:function(t){var e=this;setTimeout(function(){e.sindex=t.index+1},0==t.index?0:100)}}}},,function(t,e){t.exports={wrapper:{position:"absolute",top:0,left:0,right:0,bottom:0},slider:{flex:1},fragment:{height:100,width:100},tabs:{height:120,backgroundColor:"#87CEEB",justifyContent:"center",alignItems:"center"},"tab-text":{fontSize:50}}},,function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:["wrapper"]},[n("slider",{staticClass:["slider"],attrs:{infinite:!1},on:{change:t.changed}},t._l(20,function(e){return n("fragment",{key:e,staticClass:["fragment-content"],attrs:{jsPath:t.jsPath,visible:e==t.sindex}})})),n("div",{staticClass:["tabs"]},[n("text",{staticClass:["tab-text"]},[t._v(t._s("tab"+t.sindex))])])])},staticRenderFns:[]}},,function(t,e,n){var r,s,i=[];i.push(n(2)),r=n(0);var o=n(4);s=r=r||{},"object"!=typeof r.default&&"function"!=typeof r.default||(s=r=r.default),"function"==typeof s&&(s=s.options),s.render=o.render,s.staticRenderFns=o.staticRenderFns,s._scopeId="data-v-7fb150ba",s.style=s.style||{},i.forEach(function(t){for(var e in t)s.style[e]=t[e]}),"function"==typeof __register_static_styles__&&__register_static_styles__(s._scopeId,i),t.exports=r,t.exports.el="true",new Vue(t.exports)}]);