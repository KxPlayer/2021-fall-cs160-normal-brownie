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
    currentIndex: 0,
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
    var json = await response.json().catch(err => {console.log("username not found")});
    if (json != undefined){
      if (password == json["password"]){
        this.setState({loggingIn:false, loggedIn:true, userID:json["userid"]});
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
    const { cars, allButtonsClicked, isLoading, currentIndex, quizEntered, signingUp, loggingIn, showPasswordLengthError, showUsernameLengthError, loggedIn, userID, favorites} = this.state;
    
    if (isLoading) {
      return <p>Loading...</p>;
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
          <Button onClick={()=> this.SubmitUserSignUp(document.getElementById("username").value, document.getElementById("password").value)} id="submitSignup">Submit</Button>
        </form>
        {showUsernameLengthError ? (<p id="usernameLengthError">You need to enter a username.</p>):(<></>)}
        {showPasswordLengthError ? (<p id="passwordLengthError">The password needs to be at least 8 characters.</p>):(<></>)}
        <Button onClick={()=> this.setState({signingUp:false})} id="exitSignUp">Exit</Button>
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
      return <>
        <Header />
        {this.BackButton()}
        <div style={{ marginTop: 10, marginLeft: 10, marginRight: 10, marginBottom: 30, paddingLeft: '5%', paddingRight: '5%', paddingTop: '1%', paddingBottom: '1%', textAlign: 'center', backgroundColor: "#C2F2BA", borderRadius: 22 }}>
          <h1>Here are some cars that match your needs!</h1>
        </div>
        <div style={{ textAlign: 'center' }} id="results">
          {cars.map(car => {
            return <><p class="result">{car.make}, {car.model} a {car.transmission} car that runs on {car.fueltype} for ${car.price} with {car.doors} doors.</p>{loggedIn ? <Button onClick={()=>this.SubmitFavoriteCar(car.carid)}>Submit</Button> : <></>}</>;
          })
          }
          <Button onClick={()=> this.setState({quizEntered:false})} id="exitQuiz">Exit</Button>
        </div>
      </>
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
          <Button onClick={()=> this.SubmitUserLogIn(document.getElementById("username").value, document.getElementById("password").value)} id="submitLogin">Submit</Button>
        </form>
        <Button onClick={()=> this.setState({loggingIn:false})} id="exitLogIn">Exit</Button>
        </div></>}
        
      </>
  } else if(loggingIn && loggedIn){
    return<><Header/>
    <div style={{ marginTop: 55, marginLeft: 10, marginRight: 10, marginBottom: 30, paddingLeft: '5%', paddingRight: '5%', paddingTop: '1%', paddingBottom: '1%', textAlign: 'center', backgroundColor: "#C2F2BA", borderRadius: 22 }}>
          <h1>Here are the cars you favorited!</h1>
        </div>
        <div style={{ textAlign: 'center' }} id="results">
          <p>test</p>
          {favorites.map(favorite => {
            return <p class="result">{favorite.make}, {favorite.model} a {favorite.transmission} car that runs on {favorite.fueltype} for ${favorite.price} with {favorite.doors} doors.</p>;
          })
          }
        </div>
    <div style={{ marginTop: 55, marginLeft: '30%'}}><Button onClick={()=> this.setState({loggingIn:false})} id="exitLogIn">Exit</Button></div></>
  }
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
