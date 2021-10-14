import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { Button } from 'react-bootstrap';
import './index.css';

class App extends Component {
  state = {
    isLoading: true,
    isPressed: false,
    chosenValue: -1,
    groups: []
  };


  async componentDidMount() {
    const response = await fetch('/cars');
    const body = await response.json();
    console.log(body);
    this.setState({ groups: body, isLoading: false });   
  }

  buttonPressed(value){
    const{isPressed, chosenValue} = this.state;
    console.log("button " + value + " was pressed");
    if (value == 0){
      this.setState({chosenValue:0, isPressed:true});
    }else if (value == 1){
      this.setState({chosenValue:1, isPressed:true});
    }
  }

render(){
  const {groups, isPressed, isLoading, chosenValue} = this.state;

  if (isLoading) {
    return <p>Loading...</p>;
  }

  if(!isPressed){
  return (
    <>
    <Header />
      <div style={{marginTop:55, marginLeft:10, marginRight:10, marginBottom:30, paddingLeft:'5%', paddingRight:'5%', paddingTop:'1%', paddingBottom:'1%', textAlign:'center', backgroundColor:"#F2ECB4", borderRadius:22}}>
        <h1>Would you like a car worth more than $50,000?</h1>
        </div>
        <div style={{padding:'0', textAlign:'center'}}>
  <Button style={{marginTop:5, marginRight:15, width:200, height:200, borderWidth:0, borderRadius:13, backgroundColor:"#C6EBE9", fontSize:30}} onClick={()=>this.buttonPressed(1)}>Yes</Button>
  <Button style={{marginTop:5, marginLeft:15, width:200, height:200, borderWidth:0, borderRadius:13, backgroundColor:"#C6EBE9", fontSize:30}} onClick={()=>this.buttonPressed(0)}>No</Button>

</div>
    </>
  )
  } else{
    return <>
    <Header />
    <div style={{marginTop:55, marginLeft:10, marginRight:10, marginBottom:30, paddingLeft:'5%', paddingRight:'5%', paddingTop:'1%', paddingBottom:'1%', textAlign:'center', backgroundColor:"#C2F2BA", borderRadius:22}}>
      <h1>Here are some cars that match your needs!</h1>
      </div>
      <div style={{textAlign:'center'}}>
    {groups.map(group => {
      if(chosenValue==1){
        if(group.price > 50000){
          return <div key={group.model}><p>{group.model} for ${group.price}</p></div>;
        }
      }else if (chosenValue==0){
        if(group.price <= 50000){
          return <div key={group.model}><p>{group.model} for ${group.price}</p></div>;
        }
      }
    }) 
  }
  </div>
  </>
  }
}
}



function Header(){
  return (
    <div style={{top:0, left:0, position:'absolute', backgroundColor:"#4F90DC", height:50, width:'100%'}}>
      <h1 style={{marginTop:0}}>Car Finder</h1>
    </div>
  )
}

ReactDOM.render(
  <App />,
  document.getElementById('root')
);
