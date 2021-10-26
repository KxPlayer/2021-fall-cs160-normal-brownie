import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { Button } from 'react-bootstrap';
import './index.css';

var userInput = [null, null, null, null];
const qna = [
  {
    questionID: 0, 
    question: "Would you like a car worth more than $50,000?", 
    answer1: "Yes", 
    answer1Value: "true",
    answer2: "No",
    answer2Value: "false"
  },
  {
    questionID: 1, 
    question: "How many doors?", 
    answer1: "2", 
    answer1Value: "2",
    answer2: "4",
    answer2Value: "4"
  },
  {
    questionID: 2, 
    question: "Automatic or manual?", 
    answer1: "Automatic", 
    answer1Value: "automatic",
    answer2: "Manual",
    answer2Value: "manual"
  },
  {
    questionID: 3, 
    question: "Electric or gasoline?", 
    answer1: "Electric", 
    answer1Value: "electric",
    answer2: "Gasoline",
    answer2Value: "gasoline"
  }
];

class App extends Component {
  state = {
    isLoading: true,
    isPressed: false,
    groups: []
  };

  async componentDidMount() {
    const response = await fetch('/cars');
    const body = await response.json();
    console.log(body);
    this.setState({ groups: body, isLoading: false });
  }

  buttonPressed(index, value) {
    const { isPressed } = this.state;
    userInput[index] = value;
    console.log(userInput);
    this.forceUpdate();
    if(userInput[0] != null && userInput[1] != null && userInput[2] != null && userInput[3] != null){
        this.setState({isPressed:true});
    }
  }

  render() {
    const { groups, isPressed, isLoading } = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    if (!isPressed) {
      return (
        <>
          <Header />
          {qna.map(questionandanswer =>{
            if(userInput[questionandanswer.questionID] == null){
            return <><div style={{ marginTop: 55, marginLeft: 10, marginRight: 10, marginBottom: 30, paddingLeft: '5%', paddingRight: '5%', paddingTop: '1%', paddingBottom: '1%', textAlign: 'center', backgroundColor: "#F2ECB4", borderRadius: 22 }}>
            <h1>{questionandanswer.question}</h1>
          </div>
          <div style={{ padding: '0', textAlign: 'center' }}>
            <Button style={{ marginTop: 5, marginRight: 15, width: 200, height: 200, borderWidth: 0, borderRadius: 13, backgroundColor: "#C6EBE9", fontSize: 30 }} onClick={() => this.buttonPressed(questionandanswer.questionID, questionandanswer.answer1Value)}>{questionandanswer.answer1}</Button>
            <Button style={{ marginTop: 5, marginLeft: 15, width: 200, height: 200, borderWidth: 0, borderRadius: 13, backgroundColor: "#C6EBE9", fontSize: 30 }} onClick={() => this.buttonPressed(questionandanswer.questionID, questionandanswer.answer2Value)}>{questionandanswer.answer2}</Button>
          </div></>
          }
        }
          )
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
          {userInput.map(input => {
            return <p>{input}</p>
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
