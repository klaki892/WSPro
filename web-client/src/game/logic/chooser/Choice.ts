export default class Choice {
    text : string
    action : Function

    constructor( text : string, action : Function ) {
        this.text = text;
        this.action = action;
    }

}