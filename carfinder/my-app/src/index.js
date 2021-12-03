import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { Button } from 'react-bootstrap';
import './index.css';

var userInput = {};

const qna = [
  {
    question: "What is your price range?",
    value: "priceRange",
    answers:[
      {
        answertext:"$0 to $7500",
        value:[0, 7500]
      },
      {
        answertext:"$7500 to $35000",
        value:[7500, 35000]
      },
      {
        answertext:"$35000 to $100000",
        value:[35000, 100000]
      }] 
  },
  {
    question: "What range of years would you like the car?",
    value: "yearRange",
    answers:[
      {
        answertext:"1900-1950",
        value:[1900, 1950]
      },
      {
        answertext:"1950-2000",
        value:[1950, 2000]
      },
      {
        answertext:"2000-2022",
        value:[2000, 2022]
      }
    ]
  },
  {
    question: "How many doors would you like?", 
    value: "doors",
    answers:[
      {
        answertext:"2",
        value:2
      },
      {
        answertext:"4",
        value:4
      }]
  },
  {
    question: "Would you like an automatic or manual car?", 
    value: "transmission",
    answers:[
      {
        answertext:"Automatic",
        value:"Automatic"
      },
      {
        answertext:"Manual",
        value:"Manual"
      }]
  },
  {
    question: "Would you like an electric or gasoline car?", 
    value: "fueltype",
    answers:[
      {
        answertext:"Electric",
        value:"Electric"
      },
      {
        answertext:"Gasoline",
        value:"Gas"
      }]
  },
  {
    question: "Please select the minimum amount of mileage you would like.",
    value: "minmpgcombined",
    answers:[
      {
        answertext:"Any/Electric",
        value: null
      },
      {
        answertext:"20+",
        value: 20
      },
      {
        answertext:"30+",
        value: 30
      },
      {
        answertext:"40+",
        value: 40
      }
    ]
  }
];

class App extends Component {
  state = {
    isLoading: false,
    allButtonsClicked: false,
    cars: [],
    favorites: [],
    currentCar: [],
    currentIndex: 0,
    carLookup: false,
    quizEntered: false,
    signingUp: false,
    loggingIn: false,
    showUsernameLengthError: false,
    showPasswordLengthError: false,
    loggedIn: false,
    userID: null
  };


 async getCarsWithParam(){
    this.setState({isLoading: true});
    var firstEntry = true;
    var requestString = '/cars/personalize';
    
    for (const property in userInput) {
      if(userInput[property] === null){
        continue;
      }else{
        if(!firstEntry){
          requestString += '&';
        }else{
          requestString += '?';
          firstEntry = false;
        }
        if(property === "priceRange"){
          requestString += "minPrice=" + userInput["priceRange"][0];
          requestString += "&maxPrice=" + userInput["priceRange"][1];
        }else if(property === "yearRange"){
          requestString += "minYear=" + userInput["yearRange"][0];
          requestString += "&maxYear=" + userInput["yearRange"][1];
        }else{
          requestString += property + "=" + userInput[property];
        }
      }
    }

    console.log(requestString);
    const response = await fetch(requestString);
    const body = await response.json().catch(err => {console.log("cars not found")});
    this.setState({cars: body, isLoading: false})
  }

  async getSingleCarInfo(carid){
    this.setState({isLoading: true});
    var requestString = '/cars/id?carid=' + carid;
    const response = await fetch(requestString);
    const body = await response.json().catch(err => {console.log("car with this id not found")});
    this.setState({currentCar: body, isLoading: false});
  }

  buttonPressed(valueType, value) {
    const {currentIndex} = this.state;
    userInput[valueType] = value;
    this.setState({currentIndex:currentIndex + 1})
    this.forceUpdate();

    if(currentIndex + 1 === qna.length){
        this.setState({allButtonsClicked:true});
        this.getCarsWithParam();
        this.setState({isLoading:false});
    }
  }

  previousQuestion(){
    const{currentIndex} = this.state;
    this.setState({currentIndex:currentIndex - 1});
    if(currentIndex === qna.length){
      this.setState({allButtonsClicked:false});
      this.forceUpdate();
    }
  }

  BackButton(){
    const {currentIndex} = this.state;
    if(currentIndex > 0){
    return (<Button style={{backgroundColor: "#EB3E31", marginTop: 55, marginLeft: 20, marginRight: 10, paddingLeft: '5%', paddingRight: '5%', paddingTop: '1%', paddingBottom: '1%', textAlign: 'center', borderWidth: 0}} onClick={() => this.previousQuestion()}>Back</Button>);
    }
    else{
      return (<Button style={{backgroundColor: "#DBA29E", marginTop: 55, marginLeft: 20, marginRight: 10, paddingLeft: '5%', paddingRight: '5%', paddingTop: '1%', paddingBottom: '1%', textAlign: 'center', color: '#636363', borderWidth: 0}}>Back</Button>);
    }
  }

  SubmitUserSignUp(username, password){
    var validSignup = true;
    if(password.length < 8){
      console.log("password too short")
      this.setState({showPasswordLengthError:true});
      validSignup = false;
    } else{
      this.setState({showPasswordLengthError:false});
    }
    if(username.length === 0){
      console.log("need to input a username")
      this.setState({showUsernameLengthError:true});
      validSignup = false;
    }else{
      this.setState({showUsernameLengthError:false});
    }
    if(validSignup){
      this.setState({signingUp:false});
      const userSignUp = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ "username": username, "password": password})};
    fetch('/users', userSignUp);
    }
  }

  async getUserFavorites(){
      const {userID} = this.state;
      if (userID != null){
      this.setState({isLoading: true});
      var requestString = "/favorites/id?id=" + userID;
      console.log(requestString)
      const response = await fetch(requestString);
      const body = await response.json().catch(err => {console.log("favorites not found")});
      this.setState({favorites: body, isLoading: false})
    }
  }

  async SubmitUserLogIn(username, password){
    var requestString = '/users/username';
    requestString += '?username=' + username;
    const response = await fetch(requestString);

    var now1 = new Date();
    now1.setMonth( now1.getMonth() + 1 );
    //document.write("cookie set: " + document.cookie1)
    var json = await response.json().catch(err => {console.log("username not found")});
    
    var now1 = new Date();
    now1.setMonth( now1.getMonth() + 1 );
    //document.write("cookie set: " + document.cookie1)
    if (json != undefined){
      if (password == json["password"]){
        this.setState({loggingIn:false, loggedIn:true, userID:json["userid"]});
        document.cookie = 'username=' +requestString+'; expires=' + new Date(now1).toUTCString(); //cookie that stores username and lasts until the expiry date which is 1 month in the future. stored in plain text atm.
        //need to connect this with the 'favorites' for users to have favorites saved for them.
      }
    }
  }

  SubmitFavoriteCar(carid){
    const {userID} = this.state;
    var requestString = "/favorites/user?userid=" + userID + "&carid=" + carid;
    const addFavorite = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' }};
      fetch(requestString, addFavorite);
  
}
  
  
  render() {
    const { cars, allButtonsClicked, isLoading, currentIndex, quizEntered, signingUp, loggingIn, showPasswordLengthError, showUsernameLengthError, loggedIn, favorites, carLookup, currentCar} = this.state;
    
    if (isLoading) {
      return <><Header /><p style={{marginTop: 55}}>Loading in progress...</p></>;
    }
    if(!quizEntered && !signingUp && !loggingIn){
      return(<>
        <Header /><div style={{ marginTop: 55, textAlign: 'center'}}>
        <h1>Take a quiz to find a car that matches your needs!</h1>
        <p>If you find something you like, log in to save it to your favorites list.</p>
        <p>If you don't have an account, sign up!</p>
        </div>
        <div style={{textAlign: 'center'}}>
        <Button style={{ marginRight: 15, width: 200, height: 200, borderWidth: 0, borderRadius: 13, backgroundColor: "#C6EBE9", fontSize: 30 }} name="quizButton" onClick={()=>this.setState({quizEntered:true, currentIndex:0, allButtonsClicked:false})}>Take quiz</Button>
        <Button style={{ marginRight: 15, width: 200, height: 200, borderWidth: 0, borderRadius: 13, backgroundColor: "#C6EBE9", fontSize: 30 }} name="signUpButton" onClick={()=>this.setState({signingUp:true})}>Sign up</Button>
        <Button style={{ marginRight: 15, width: 200, height: 200, borderWidth: 0, borderRadius: 13, backgroundColor: "#C6EBE9", fontSize: 30 }} name="logInButton" onClick={()=>{this.setState({loggingIn:true}); this.getUserFavorites();}}>Log in</Button></div>
        </>);
    
  }else if(signingUp){
      return <>{<><Header />
        <div style={{ marginTop: 55, marginLeft: '30%'}}>
        <form>
          <label>
            Username: <input type="text" name="username" id="username" required/>
          </label>
          <br></br>
          <label>
            Password: <input type="password" name="password" id="password" minLength="8" required />
          </label>
          <br></br>
          <Button style={{backgroundColor:"#a2a6a3", borderWidth:0, borderRadius:13}} onClick={()=> this.SubmitUserSignUp(document.getElementById("username").value, document.getElementById("password").value)} id="submitSignup">Submit</Button>
        </form>
        {showUsernameLengthError ? (<p id="usernameLengthError">You need to enter a username.</p>):(<></>)}
        {showPasswordLengthError ? (<p id="passwordLengthError">The password needs to be at least 8 characters.</p>):(<></>)}
        <Button style={{backgroundColor:"#a2a6a3", borderWidth:0, borderRadius:13}} onClick={()=> this.setState({signingUp:false})} id="exitSignUp">Exit</Button>
        </div></>}
        
      </>
    }else if(quizEntered){
    if (!allButtonsClicked) {
      var questionandanswer = qna[currentIndex];
      return (
        <>
          <Header />
          {this.BackButton()}
            <div style={{ marginTop: 10, marginLeft: 10, marginRight: 10, marginBottom: 30, paddingLeft: '5%', paddingRight: '5%', paddingTop: '1%', paddingBottom: '1%', textAlign: 'center', backgroundColor: "#F2ECB4", borderRadius: 22}}>
            <h1>{questionandanswer.question}</h1>
          </div>
          
          <div style={{ padding: '0', textAlign: 'center', marginTop: 10 }}>
            {questionandanswer.answers.map(answerbuttons =>{
              return <Button style={{ marginTop: 0, marginRight: 15, width: 250, height: 200, borderWidth: 0, borderRadius: 13, backgroundColor: "#C6EBE9", fontSize: 25 }} id={questionandanswer.value + answerbuttons.value} onClick={() => {this.buttonPressed(questionandanswer.value, answerbuttons.value); console.log(questionandanswer.value + answerbuttons.value);}}>{answerbuttons.answertext}</Button>
            })}
          </div>    
        </>

      )
    } else {
      if(carLookup){
        var car = currentCar[0];
        return <><Header />
        <div style={{marginLeft:'30%', marginTop: 55}}>
        <h1>{car.make} {car.model} {car.year}</h1>
        <p>Price: ${car.price}</p>
        <p>Doors: {car.doors}</p>
        <p>Fuel type: {car.fueltype}</p>
        <p>EPA Passenger: {car.epapassenger}</p>
        <p>Transmission: {car.transmission}</p>
        <p>Engine: {car.engine}</p>
        <p>Weight: {car.weight}</p>
        <p>Length: {car.length}</p>
        <p>Width: {car.width}</p>
        <p>Towing capacity: {car.towingcapacity}</p>
        <p>Trunk capacity: {car.trunkcapacity}</p>
        <p>Horsepower: {car.horsepower}</p>
        <p>Torque: {car.torque}</p>
        <p>Torque RPM: {car.torquerpm}</p>
        <p>MPG (City): {car.mpgcity}</p>
        <p>MPG (Highway): {car.mpghighway}</p>
        <p>MPG (Combined): {car.mpgcombined}</p>
        <p>Luxury car?: {car.luxury}</p>
        <p>Sports car?: {car.sport}</p>
        </div>
        <div style={{textAlign: 'center'}}>
          <Button style={{backgroundColor:"#a2a6a3", borderWidth:0, borderRadius:13}} onClick={()=> this.setState({carLookup:false})} id="exitCarInfo">Exit</Button>
        </div>
        </>;
      }else{
      return <>
        <Header />
        {this.BackButton()}
        <div style={{ marginTop: 10, marginLeft: 10, marginRight: 10, marginBottom: 30, paddingLeft: '5%', paddingRight: '5%', paddingTop: '1%', paddingBottom: '1%', textAlign: 'center', backgroundColor: "#C2F2BA", borderRadius: 22 }}>
          <h1>Here are some cars that match your needs! Click on a car's name to see more information!</h1>
        </div>
        <div style={{ textAlign: 'center'}} id="results">
          {cars.map(car => {
            return <div class="result" style={{display:'flex', justifyContent: 'center'}}><Button style={{borderWidth:0, backgroundColor:'white'}} onClick={()=>{this.getSingleCarInfo(car.carid); this.setState({carLookup:true}); }}>{car.make} {car.model} {car.year} ${car.price} </Button>{loggedIn ? <Button style={{backgroundColor:"#a2a6a3", borderWidth:0, borderRadius:13}} onClick={()=>this.SubmitFavoriteCar(car.carid)}>Favorite</Button> : <></>}</div>;
          })
          }
        </div>
        <div style={{textAlign: 'center'}}><Button style={{backgroundColor:"#a2a6a3", borderWidth:0, borderRadius:13}} onClick={()=> this.setState({quizEntered:false})} id="exitQuiz">Exit</Button></div>
      </>
      }
    }
  } else if(loggingIn && !loggedIn){
    return <>{<><Header />
        <div style={{ marginTop: 55, marginLeft: '30%'}}>
        <form>
          <label>
            Username: <input type="text" name="username" id="username" required/>
          </label>
          <br></br>
          <label>
            Password: <input type="password" name="password" id="password" minLength="8" required />
          </label>
          <br></br>
          <Button style={{backgroundColor:"#a2a6a3", borderWidth:0, borderRadius:13}} onClick={()=> this.SubmitUserLogIn(document.getElementById("username").value, document.getElementById("password").value)} id="submitLogin">Submit</Button>
        </form>
        <Button style={{backgroundColor:"#a2a6a3", borderWidth:0, borderRadius:13}} onClick={()=> this.setState({loggingIn:false})} id="exitLogIn">Exit</Button>
        </div></>}
        
      </>
  } else if(loggingIn && loggedIn){
    if(carLookup){
      var car = currentCar[0];
        return <><Header />
        <div style={{marginLeft:'30%', marginTop: 55}}>
        <h1>{car.make} {car.model} {car.year}</h1>
        <p>Price: ${car.price}</p>
        <p>Doors: {car.doors}</p>
        <p>Fuel type: {car.fueltype}</p>
        <p>EPA Passenger: {car.epapassenger}</p>
        <p>Transmission: {car.transmission}</p>
        <p>Engine: {car.engine}</p>
        <p>Weight: {car.weight}</p>
        <p>Length: {car.length}</p>
        <p>Width: {car.width}</p>
        <p>Towing capacity: {car.towingcapacity}</p>
        <p>Trunk capacity: {car.trunkcapacity}</p>
        <p>Horsepower: {car.horsepower}</p>
        <p>Torque: {car.torque}</p>
        <p>Torque RPM: {car.torquerpm}</p>
        <p>MPG (City): {car.mpgcity}</p>
        <p>MPG (Highway): {car.mpghighway}</p>
        <p>MPG (Combined): {car.mpgcombined}</p>
        <p>Luxury car?: {car.luxury}</p>
        <p>Sports car?: {car.sport}</p>
        </div>
        <div style={{textAlign: 'center'}}>
          <Button style={{backgroundColor:"#a2a6a3", borderWidth:0, borderRadius:13}} onClick={()=> this.setState({carLookup:false})} id="exitCarInfo">Exit</Button>
        </div>
        </>
    }else{
    return<><Header/>
    <div style={{ marginTop: 55, marginLeft: 10, marginRight: 10, marginBottom: 30, paddingLeft: '5%', paddingRight: '5%', paddingTop: '1%', paddingBottom: '1%', textAlign: 'center', backgroundColor: "#C2F2BA", borderRadius: 22 }}>
          <h1>Here are the cars you favorited!</h1>
        </div>
        <div style={{ textAlign: 'center'}} id="results">
          {favorites.map(favorite => {
            return <div class="result" style={{display:'flex', justifyContent: 'center'}}><Button style={{borderWidth:0, backgroundColor:'white'}} onClick={()=>{this.getSingleCarInfo(favorite.carid); this.setState({carLookup:true}); }}>{favorite.make} {favorite.model} {favorite.year} ${favorite.price} </Button><Button style={{backgroundColor:"#a2a6a3", borderWidth:0, borderRadius:13}} onClick={()=>console.log('Would be removing car with id of ' + favorite.carid)}>Remove</Button></div>
          })
          }
        </div>
    <div style={{textAlign: 'center'}}><Button style={{backgroundColor:"#a2a6a3", borderWidth:0, borderRadius:13}} onClick={()=> this.setState({loggingIn:false})} id="exitLogIn">Exit</Button></div></>
  }}
  }
}

function Header() {
  return (
    <div style={{ top: 0, left: 0, position: 'absolute', backgroundColor: "#4F90DC", height: 50, width: '100%' }}>
      <h1 style={{ marginTop: 0 }}>Car Finder</h1>
    </div>
  )
}

ReactDOM.render(
  <App />,
  document.getElementById('root')
);
