/**
 * 料金検索（申請作成画面用）
 * @param {type} index
 * @returns {undefined}
 */
function callApiFromMakePage(index) {
    var from = document.getElementById("entries:" + index + ":section_from").value;
    var to = document.getElementById("entries:" + index + ":section_to").value;
    callApi(from, to);
}

/**
 * 料金検索（申請照会画面用）
 * @param {type} from
 * @param {type} to
 * @returns {undefined}
 */
function callApiFromDetailPage(from, to) {
    callApi(from, to);
}

/**
 * Yahoo路線情報表示
 * @param {type} from
 * @param {type} to
 * @returns {undefined}
 */
function callApi(from, to) {
    var url = "https://transit.yahoo.co.jp/search/result"
                + "?from=" + encodeURIComponent(from)
                + "&to=" + encodeURIComponent(to)
                + "&ticket=ic&shin=1&ex=1&al=1&hb=1&lb=1&sr=1&type=5&s=1&ei=utf-8";
    var option = "width=800, height=600, menubar=no, toolbar=no, location=no, status=no, resizable=yes, scrollbars=yes";
    window.open(url, null, option);
}
