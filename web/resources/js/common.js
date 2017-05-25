/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function callApiFromMakePage(index) {
    var from = document.getElementById("entries:" + index + ":section_from").value;
    var to = document.getElementById("entries:" + index + ":section_to").value;
    alert(from);
    alert(to);
}

function callApiFromDetailPage(from, to) {
    alert(from);
    alert(to);
}
