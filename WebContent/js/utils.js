var romanianASCII=new Array(194,226,258,259,206,238,351,350,355,354,537,536,539,538);

function getValueOfSelectedItem(dropDownListId)
{
	var ddllist= document.getElementById(dropDownListId);
	var itemName= ddllist.options[ddllist.selectedIndex].value;
	return itemName;
}

function isCheckBoxChecked(checkBoxName){
	if (document.getElementById(checkBoxName).checked == 1)
		return 1;
	else
		return 0; 
}

function decodeString(text){
	var escapedText = "";
	
	for (i=0; i < text.length; i++){
		var charCode = text.charCodeAt(i);
		if (romanianASCII.indexOf(charCode) != -1){
			escapedText += "&#" + charCode + ";";
		} else {
			escapedText += text.charAt(i);
		}
	}
	
	return escapedText;
}