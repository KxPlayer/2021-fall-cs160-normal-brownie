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
    answer1: "Yes", 
    answer1Value: true,
    answer2: "No",
    answer2Value: false
  },
  {
    question: "How many doors?", 
    value: "doors",
    answer1: "2", 
    answer1Value: 2,
    answer2: "4",
    answer2Value: 4
  },
  {
    question: "Automatic or manual?", 
    value: "transmission",
    answer1: "Automatic", 
    answer1Value: "Automatic",
    answer2: "Manual",
    answer2Value: "Manual"
  },
  {
    question: "Electric or gasoline?", 
    value: "fueltype",
    answer1: "Electric", 
    answer1Value: "Electric",
    answer2: "Gasoline",
    answer2Value: "Gas"
  }
];

class App extends Component {
  state = {
    isLoading: false,
    allButtonsClicked: false,
    cars: []
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

    console.log(requestString);
    const response = await fetch(requestString);
    const body = await response.json();
    console.log(body);
    this.setState({cars: body, isLoading: false})
  }

  buttonPressed(valueType, value) {
    userInput[valueType] = value;
    this.forceUpdate();
    var hasNull = false;
    for (const property in userInput) {
      if(userInput[property] == null){
        hasNull = true;
        break;
      }else{
        continue;
      }
    }

    if(!hasNull){
        this.setState({allButtonsClicked:true});
        this.getCarsWithParam();
    }
  }

  render() {
    const { cars, allButtonsClicked, isLoading } = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    if (!allButtonsClicked) {
      return (
        <>
          <Header />
          {qna.map(questionandanswer =>{
            if(userInput[questionandanswer.value] == null){
            return <><div style={{ marginTop: 55, marginLeft: 10, marginRight: 10, marginBottom: 30, paddingLeft: '5%', paddingRight: '5%', paddingTop: '1%', paddingBottom: '1%', textAlign: 'center', backgroundColor: "#F2ECB4", borderRadius: 22 }}>
            <h1>{questionandanswer.question}</h1>
          </div>
          <div style={{ padding: '0', textAlign: 'center' }}>
            <Button style={{ marginTop: 5, marginRight: 15, width: 200, height: 200, borderWidth: 0, borderRadius: 13, backgroundColor: "#C6EBE9", fontSize: 30 }} onClick={() => this.buttonPressed(questionandanswer.value, questionandanswer.answer1Value)}>{questionandanswer.answer1}</Button>
            <Button style={{ marginTop: 5, marginLeft: 15, width: 200, height: 200, borderWidth: 0, borderRadius: 13, backgroundColor: "#C6EBE9", fontSize: 30 }} onClick={() => this.buttonPressed(questionandanswer.value, questionandanswer.answer2Value)}>{questionandanswer.answer2}</Button>
          </div>
          </>
              }
            })
          }

        </>

      )
    } else {
      return <>
        <Header />
        <div style={{ marginTop: 55, marginLeft: 10, marginRight: 10, marginBottom: 30, paddingLeft: '5%', paddingRight: '5%', paddingTop: '1%', paddingBottom: '1%', textAlign: 'center', backgroundColor: "#C2F2BA", borderRadius: 22 }}>
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
