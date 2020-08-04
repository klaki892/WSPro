import * as React from 'react';
import * as PIXI from "pixi.js";
import testCardFront from '../resources/testCardFront.jpg'
import cardBack from '../resources/cardBack.png';
import PlayArea from "./logic/field/PlayArea";
import PlayCard from "./logic/PlayCard";
import {ZoneName} from "./logic/field/ZoneName";

export class PixiComponent extends React.Component {
    app: PIXI.Application | any;
    gameCanvas: HTMLDivElement | any;

    constructor({props, context}: { props: any, context: any }) {
        super(props, context);

    }

    /**
     * After mounting, add the PIXI Renderer to the div and start the Application.
     */
    componentDidMount() {
        this.app = new PIXI.Application({
            width: window.innerWidth,
            height: window.innerHeight,
            antialias: true,
            transparent: false,
            resolution: 1,
            forceCanvas: true
        });
        this.gameCanvas.appendChild(this.app.view);

        this.app.renderer.backgroundColor = 0xff;
        this.app.renderer.autoResize = true;

        this.app.loader.add("cardBack", cardBack)
            .add("cardFront", testCardFront)
            .load(() => this.setup(this.app))
        // this.setup(this.app)

        //auto resizing
        window.addEventListener('resize', this.autoResize);

        this.app.start();
    }

    private autoResize() {
        // this.app.renderer.resize(window.innerHeight, window.innerWidth);
        if (this.app !== undefined) {
            let parent = this.app.view.parentNode;
            this.app.renderer.resize(this.app.screen.width, this.app.screen.height);
        }

    }

    setup(app: PIXI.Application){
        let sprite = new PlayCard(
            app.loader.resources["cardFront"].texture,
            app.loader.resources["cardBack"].texture
        )
        // let sprite = PIXI.Sprite.from('https://i.imgur.com/rRoIHdc.png');
       sprite.x = 100;
       sprite.y = 100;



       let playerStageArea = new PlayArea();
       playerStageArea.x = 300;
       app.stage.addChild(playerStageArea);

        let card2 = new PlayCard(
            app.loader.resources["cardFront"].texture,
            app.loader.resources["cardBack"].texture
        );
        card2.flipCard();

        app.stage.addChild(card2);

        let deckZone = playerStageArea.zones.get(ZoneName.DECK);
        if (deckZone !== undefined){
            card2.position = deckZone.getGlobalPosition();
        }

       app.stage.addChild(sprite);
       let message = new PIXI.Text("Hello World");
       app.stage.addChild(message);
       message.position.set(sprite.x, sprite.y);
       this.app.ticker.add((delta: any) =>{
           // sprite.y += 1;
           message.position = sprite.position;
       })

        /*Todo:
        *  Make classes for cards and playzones
        * Add Special zones for Stackable cards? (hand and stock)
        * Create function to move a card from zone to zone
        * Look into Hexi and other GUI based libraries for fast implementing buttons and other technology
        * (We Might have to downgrade to get capability. */
    }

    /**
     * Stop the Application when unmounting.
     */
    componentWillUnmount() {
        this.app.stop();
    }

    /**
     * Simply render the div that will contain the PIXI Renderer.
     */
    render() {
        let component = this;
        return (
            <React.Fragment>
            <div ref={(thisDiv) => {component.gameCanvas = thisDiv}} />
            {/*<img src={cardBack}/>*/}
            </React.Fragment>
        );
    }
}
