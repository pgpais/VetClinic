getPets();

function getPets() {
  var xhttp = new XMLHttpRequest();
  xhttp.open("GET", "http://localhost:8080/pets");
  xhttp.setRequestHeader("Content-type", "application/json");
  xhttp.send();
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState == 4 && xhttp.status == 200) {
      let innerHTML = "";
      let response = JSON.parse(xhttp.response);
      console.log(response);
      response.forEach(obj => {
        let pet = obj["pet"];
        console.log(pet["name"]);
        innerHTML += "<li>" + pet["name"] + "</li> \n";
      });

      console.log(list)
      list.innerHTML = innerHTML;
    }
  };

  let listHTML = document.getElementById("list")
}
