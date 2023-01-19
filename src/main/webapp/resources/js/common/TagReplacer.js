class TagReplacer {
    constructor(tag) {
        this.tag = tag;
        this.regex = /\@\{.*\}/g;
    }

    replace(param) {
        const nodeIter = document.createNodeIterator(
            this.tag
            , NodeFilter.SHOW_ALL
            , node => {
                return node.nodeType == Node.ELEMENT_NODE || node.nodeType == Node.TEXT_NODE;
            });
        let crnt;
        while (crnt = nodeIter.nextNode()) {
            if (crnt.nodeType == Node.ELEMENT_NODE) {
                crnt.getAttributeNames().forEach(attrName => {
                    let attrValue = crnt.getAttribute(attrName);
                    const match = attrValue.match(this.regex);
                    match && match.forEach(val => {
                        let sub = val.substring(2, val.length-1);
                        attrValue = attrValue.replaceAll(val, param[sub]);
                    });
                    crnt.setAttribute(attrName, attrValue);
                });
            } else if (crnt.nodeType == Node.TEXT_NODE) {
                let text = crnt.nodeValue;
                const match = text.match(this.regex);
                match && match.forEach(val => {
                    let sub = val.substring(2, val.length-1);
                    text = text.replaceAll(val, param[sub]);
                });
                crnt.textContent = text;
            }
        }
    }
}