import ChoiceSelector from "../logic/chooser/ChoiceSelector";

export default class ChoiceSelectorView extends PIXI.Container{
    private chooser: ChoiceSelector;

    private background! : PIXI.Sprite

    constructor(chooser: ChoiceSelector) {
        super();
        this.chooser = chooser;

        this.visible = false;

    }



}
