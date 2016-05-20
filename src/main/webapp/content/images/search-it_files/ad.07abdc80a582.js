(function() {
  var error, img_ad, img_link, text_link, text_url, txt_ad, url;

  if (!document.getElementsByClassName) {
    document.getElementsByClassName = function(search) {
      var d, elements, i, pattern, results;
      d = document;
      elements = void 0;
      pattern = void 0;
      i = void 0;
      results = [];
      if (d.querySelectorAll) {
        return d.querySelectorAll('.' + search);
      }
      if (d.evaluate) {
        pattern = './/*[contains(concat(\' \', @class, \' \'), \' ' + search + ' \')]';
        elements = d.evaluate(pattern, d, null, 0, null);
        while (i = elements.iterateNext()) {
          results.push(i);
        }
      } else {
        elements = d.getElementsByTagName('*');
        pattern = new RegExp('(^|\\s)' + search + '(\\s|$)');
        i = 0;
        while (i < elements.length) {
          if (pattern.test(elements[i].className)) {
            results.push(elements[i]);
          }
          i++;
        }
      }
      return results;
    };
  }

  img_ad = document.getElementsByClassName("ad")[0];

  try {
    img_link = img_ad.parentNode;
    url = img_link.attributes[0].nodeValue;
    txt_ad = document.getElementsByClassName("ads")[0];
    text_link = txt_ad.children[0];
    text_url = text_link.attributes[0].nodeValue;
    img_link.onclick = function() {
      window.open(url);
      return false;
    };
    text_link.onclick = function() {
      window.open(text_url);
      return false;
    };
  } catch (_error) {
    error = _error;
    "0"
  }

}).call(this);
