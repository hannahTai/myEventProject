/**
 * @license Copyright (c) 2003-2018, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function(config) {
    // Define changes to default configuration here. For example:
    // config.language = 'fr';
    // config.uiColor = '#AADC6E';



    config.language = 'zh';
    config.height = 500;
    config.toolbarCanCollapse = true;



    config.toolbarGroups = [
        { name: 'document', groups: ['mode', 'document', 'doctools'] },
        { name: 'forms', groups: ['forms'] },
        { name: 'clipboard', groups: ['clipboard', 'undo'] },
        { name: 'basicstyles', groups: ['basicstyles', 'cleanup'] },
        { name: 'paragraph', groups: ['list', 'indent', 'blocks', 'align', 'bidi', 'paragraph'] },
        { name: 'links', groups: ['links'] },
        { name: 'insert', groups: ['insert'] },
        '/',
        { name: 'styles', groups: ['styles'] },
        { name: 'colors', groups: ['colors'] },
        { name: 'tools', groups: ['tools'] },
        { name: 'editing', groups: ['find', 'selection', 'spellchecker', 'editing'] },
        { name: 'others', groups: ['others'] },
        { name: 'about', groups: ['about'] }
    ];

    config.removeButtons = 'Source,Save,Templates,NewPage,Preview,Print,Cut,Copy,Paste,PasteText,PasteFromWord,Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,About,Iframe,Flash,BidiLtr,BidiRtl,Language';



};