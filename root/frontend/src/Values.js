import React, { Component } from 'react'

class Values extends Component {
    render() {
        return (
            <div>
                <div>{this.props.values}</div>
                <button onClick={this.props.onGetValues}>
                    Get Values!
                </button>
            </div>
        );
    }
}

export default Values;