let para = document.getElementById("info-display")

function displayInfo(){
    para.textContent = "First name: " + document.getElementById("first-name").value +
    "\nLast name: " + document.getElementById("last-name").value + "\nOccupation: " +
    document.getElementById("job").value + "\nCountry: " + document.getElementById("country_drop_down").value

    console.log("this works but won't stay. Weird")
}

