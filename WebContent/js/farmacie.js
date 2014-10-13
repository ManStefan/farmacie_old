function setViewProdusFormFieldValue(elementId, elementValue) {
    document.viewProdusForm.elements[elementId].value = elementValue;
}

function viewProdusFormSubmit(url) {

    if (url != "") {
        document.viewProdusForm.action = url;
    }
    document.viewProdusForm.submit();
}