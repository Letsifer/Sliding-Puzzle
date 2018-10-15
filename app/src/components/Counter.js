import React, {Component} from 'react';

class Counter extends Component {
    state = {
        number: 0
    }

    render() {
        return (
                <div>
                    <h4>Counter</h4>
                    <div>
                        <button onClick={this.decrement}>decr</button>    
                        {this.state.number}
                        <button onClick={this.increment}>incr</button>                    
                    </div>
                </div>
                );
    }

    decrement = () => {
        this.setState({
            number: this.state.number - 1
        })
    }
    increment = () => {
        this.setState({
            number: this.state.number + 1
        })
    }
}

export default Counter