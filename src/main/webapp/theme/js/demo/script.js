// function for setting background color
changeBackgroundColor = function (variable) {
    const backgroundElement = document.querySelector("#accordionSidebar");
    backgroundElement.classList.remove("bg-gradient-primary");
    backgroundElement.classList.remove("bg-gradient-dark");
    backgroundElement.classList.remove("bg-gradient-danger");
    backgroundElement.classList.add(variable);
};

// function for setting button color
changeButtonColor = function (variable) {
    const buttonElement = document.querySelectorAll(".btn");
    buttonElement.forEach(function (button) {
        button.classList.remove("btn-primary");
        button.classList.remove("btn-danger");
        button.classList.remove("btn-dark");
        if (variable === "bg-gradient-dark") {
            button.classList.add("btn-dark");
        } else if (variable === "bg-gradient-danger") {
            button.classList.add("btn-danger");
        } else {
            button.classList.add("btn-primary");
        }
    });
};

// function for setting font color
changeFontColor = function (variable) {
    const fontElement = document.querySelectorAll(".font-change");
    fontElement.forEach(function (font) {
        font.classList.remove("text-primary");
        font.classList.remove("text-danger");
        font.classList.remove("text-dark");
        if (variable === "bg-gradient-dark") {
            font.classList.add("text-dark");
        } else if (variable === "bg-gradient-danger") {
            font.classList.add("text-danger");
        } else {
            font.classList.add("text-primary");
        }
    });
}

// listen for click event on three tabs in THEME ACCORDION to define chosen color for website theme
if (document.body.contains(document.querySelector("#colorPicker"))) {
    const chosenColor = document.querySelector("#colorPicker").children;
    const chosenColorArray = Array.from(chosenColor);
    chosenColorArray.forEach(function (element) {
        element.addEventListener("click", function () {
            // set local storage variable on click event for later usage
            window.localStorage.setItem("clickedColor", element.id);
            // based on click event, set nav background color
            changeBackgroundColor(element.id);
            console.log(element.id);
            // based on click event, set button color
            changeButtonColor(element.id);
            // based on click event, set font color
            changeFontColor(element.id);
        });
    });
}

// if no click event took place, use localstorage variable if it's not null
let fixedColor = window.localStorage.getItem("clickedColor");
if (fixedColor !== null) {
    // set background color to previously chosen color
    changeBackgroundColor(fixedColor);
    // set button color to previously chosen color
    changeButtonColor(fixedColor);
    // set font color to previously chosen color
    changeFontColor(fixedColor);
}
