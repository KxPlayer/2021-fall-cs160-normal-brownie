import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { Button } from 'react-bootstrap';
import './index.css';

var userInput = {
  "tempFilter":null,
  "doors":null,
  "transmission":null,
  "fueltype":null
};

const qna = [
  {
    question: "Would you like a car worth more than $50,000?",
    value: "tempFilter",
    answers:[
      {
        answertext:"Yes",
        value:true
      },
      {
        answertext:"No",
        value:false
      }] 
  },
  {
    question: "How many doors?", 
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
    question: "Automatic or manual?", 
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
    question: "Electric or gasoline?", 
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
  }
];

class App extends Component {
  state = {
    isLoading: false,
    allButtonsClicked: false,
    cars: [],
    currentIndex: 0,
    quizEntered: false,
    signingUp: false,
    loggingIn: false,
    showUsernameLengthError: false,
    showPasswordLengthError: false
  };


 async getCarsWithParam(){
    this.setState({isLoading: true});
    var firstEntry = true;
    var requestString = '/cars/personalize';
    
    for (const property in userInput) {
      if(userInput[property] == null){
        continue;
      }else{
        if(!firstEntry){
          requestString += '&';
        }else{
          requestString += '?';
          firstEntry = false;
        }
        if(property == "tempFilter"){
          if(userInput["tempFilter"] == true){
            requestString += "minPrice=50000";
          }else{
            requestString += "maxPrice=50000";
          }
        }else{
          requestString += property + "=" + userInput[property];
        }
      }
    }

    const response = await fetch(requestString);
    const body = await response.json();
    this.setState({cars: body, isLoading: false})
  }

  buttonPressed(valueType, value) {
    const {currentIndex, isLoading} = this.state;
    userInput[valueType] = value;
    this.setState({currentIndex:currentIndex + 1})
    this.forceUpdate();


    if(currentIndex + 1 == qna.length){
        this.setState({allButtonsClicked:true});
        //this.getCarsWithParam();
        this.setState({isLoading:false});

        var firstEntry = true;
    var requestString = '/cars/personalize';
    
    for (const property in userInput) {
      if(userInput[property] == null){
        continue;
      }else{
        if(!firstEntry){
          requestString += '&';
        }else{
          requestString += '?';
          firstEntry = false;
        }
        if(property == "tempFilter"){
          if(userInput["tempFilter"] == true){
            requestString += "minPrice=50000";
          }else{
            requestString += "maxPrice=50000";
          }
        }else{
          requestString += property + "=" + userInput[property];
        }
      }
    }
    console.log(requestString);
    }
  }

  previousQuestion(){
    const{currentIndex} = this.state;
    this.setState({currentIndex:currentIndex - 1});
    if(currentIndex == qna.length){
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
    console.log(username, password);
    var validSignup = true;
    if(password.length < 8){
      console.log("password too short")
      this.setState({showPasswordLengthError:true});
      validSignup = false;
    } else{
      this.setState({showPasswordLengthError:false});
    }
    if(username.length == 0){
      console.log("need to input a username")
      this.setState({showUsernameLengthError:true});
      validSignup = false;
    }else{
      this.setState({showUsernameLengthError:false});
    }
    if(validSignup){
      this.setState({signingUp:false});
    }
    // submit here?
  }

  render() {
    const { cars, allButtonsClicked, isLoading, currentIndex, quizEntered, signingUp, loggingIn, showPasswordLengthError, showUsernameLengthError} = this.state;

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
      <Button style={{ marginRight: 15, width: 200, height: 200, borderWidth: 0, borderRadius: 13, backgroundColor: "#C6EBE9", fontSize: 30 }} onClick={()=>this.setState({quizEntered:true})}>Take quiz</Button>
      <Button style={{ marginRight: 15, width: 200, height: 200, borderWidth: 0, borderRadius: 13, backgroundColor: "#C6EBE9", fontSize: 30 }} onClick={()=>this.setState({signingUp:true})}>Sign up</Button>
      <Button style={{ marginRight: 15, width: 200, height: 200, borderWidth: 0, borderRadius: 13, backgroundColor: "#C6EBE9", fontSize: 30 }} onClick={()=>this.setState({loggingIn:true})}>Log in</Button></div></>);
    
  }else if(signingUp){
      if (showPasswordLengthError && showUsernameLengthError){
        return (<><Header />
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
            <Button onClick={()=> this.SubmitUserSignUp(document.getElementById("username").value, document.getElementById("password").value)}>Submit</Button>
          </form>
          
        <p>You need to enter a username.</p>
        <p>The password needs to be at least 8 characters.</p></div>
        </>);
      }else if (showPasswordLengthError){
        return (<><Header />
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
            <Button onClick={()=> this.SubmitUserSignUp(document.getElementById("username").value, document.getElementById("password").value)}>Submit</Button>
          </form>
          
          <p>The password needs to be at least 8 characters.</p></div>
          </>);
      }else if (showUsernameLengthError){
      return (<><Header />
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
          <Button onClick={()=> this.SubmitUserSignUp(document.getElementById("username").value, document.getElementById("password").value)}>Submit</Button>
        </form>
        <p>You need to enter a username.</p></div></>
        );
    }else{
      return (<><Header />
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
        <Button onClick={()=> this.SubmitUserSignUp(document.getElementById("username").value, document.getElementById("password").value)}>Submit</Button>
      </form>
      </div>
      </>);
    }

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
              return <Button style={{ marginTop: 0, marginRight: 15, width: 200, height: 200, borderWidth: 0, borderRadius: 13, backgroundColor: "#C6EBE9", fontSize: 30 }} onClick={() => this.buttonPressed(questionandanswer.value, answerbuttons.value)}>{answerbuttons.answertext}</Button>
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
        <div style={{ textAlign: 'center' }}>
          {cars.map(car => {
            return <p>{car.make}, {car.model} a {car.transmission} car that runs on {car.fueltype} for ${car.price} with {car.doors} doors.</p>
          })
          }
        </div>
      </>
    }
  } else if(loggingIn){
    return <>
    <Header/>
    </>
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
