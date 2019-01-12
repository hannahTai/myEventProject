/**
 * Copyright (c) 2003-2018, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or https://ckeditor.com/legal/ckeditor-oss-license
 */

/* exported initSample */

if ( CKEDITOR.env.ie && CKEDITOR.env.version < 9 )
	CKEDITOR.tools.enableHtml5Elements( document );

// The trick to keep the editor in the sample quite small
// unless user specified own height.
CKEDITOR.config.height = 500;
CKEDITOR.config.width = 'auto';










// -----活動介紹-----ckEditor-----初始化-----
var initInfoEditor = ( function() {
	var wysiwygareaAvailable = isWysiwygareaAvailable(),
		isBBCodeBuiltIn = !!CKEDITOR.plugins.get( 'bbcode' );

	return function() {
		var editorElement = CKEDITOR.document.getById( 'infoEditor' );

		// :(((
		if ( isBBCodeBuiltIn ) {
			editorElement.setHtml(
				'Hello world!\n\n' +
				'I\'m an instance of [url=https://ckeditor.com]CKEditor[/url].'
			);
		}

		// Depending on the wysiwygarea plugin availability initialize classic or inline editor.
		if ( wysiwygareaAvailable ) {
//			CKEDITOR.replace( 'infoEditor' );
			CKEDITOR.replace( 'infoEditor', {
				removePlugins: 'image',
				extraPlugins: 'easyimage',
				cloudServices_tokenUrl: 'https://36762.cke-cs.com/token/dev/UM6e5GmaKWwu89mPGLfo7csdkFHnd6pThBTSG6KZ3PZIOnotQZZOpXzO92Lu',
				cloudServices_uploadUrl: 'https://36762.cke-cs.com/easyimage/upload/'
			} );
		} else {
			editorElement.setAttribute( 'contenteditable', 'true' );
			CKEDITOR.inline( 'infoEditor' );

			// TODO we can consider displaying some info box that
			// without wysiwygarea the classic editor may not work.
		}
	};

	function isWysiwygareaAvailable() {
		// If in development mode, then the wysiwygarea must be available.
		// Split REV into two strings so builder does not replace it :D.
		if ( CKEDITOR.revision == ( '%RE' + 'V%' ) ) {
			return true;
		}

		return !!CKEDITOR.plugins.get( 'wysiwygarea' );
	}
} )();










// -----注意事項-----ckEditor-----初始化-----
var initNoticesEditor = ( function() {
	var wysiwygareaAvailable = isWysiwygareaAvailable(),
		isBBCodeBuiltIn = !!CKEDITOR.plugins.get( 'bbcode' );

	return function() {
		var editorElement = CKEDITOR.document.getById( 'noticesEditor' );

		// :(((
		if ( isBBCodeBuiltIn ) {
			editorElement.setHtml(
				'Hello world!\n\n' +
				'I\'m an instance of [url=https://ckeditor.com]CKEditor[/url].'
			);
		}

		// Depending on the wysiwygarea plugin availability initialize classic or inline editor.
		if ( wysiwygareaAvailable ) {
//			CKEDITOR.replace( 'noticesEditor' );
			CKEDITOR.replace( 'noticesEditor', {
				removePlugins: 'image',
				extraPlugins: 'easyimage',
				cloudServices_tokenUrl: 'https://36762.cke-cs.com/token/dev/UM6e5GmaKWwu89mPGLfo7csdkFHnd6pThBTSG6KZ3PZIOnotQZZOpXzO92Lu',
				cloudServices_uploadUrl: 'https://36762.cke-cs.com/easyimage/upload/'
			} );
		} else {
			editorElement.setAttribute( 'contenteditable', 'true' );
			CKEDITOR.inline( 'noticesEditor' );

			// TODO we can consider displaying some info box that
			// without wysiwygarea the classic editor may not work.
		}
	};

	function isWysiwygareaAvailable() {
		// If in development mode, then the wysiwygarea must be available.
		// Split REV into two strings so builder does not replace it :D.
		if ( CKEDITOR.revision == ( '%RE' + 'V%' ) ) {
			return true;
		}

		return !!CKEDITOR.plugins.get( 'wysiwygarea' );
	}
} )();










// -----購票提醒-----ckEditor-----初始化-----
var initEticpurchaserulesEditor = ( function() {
	var wysiwygareaAvailable = isWysiwygareaAvailable(),
		isBBCodeBuiltIn = !!CKEDITOR.plugins.get( 'bbcode' );

	return function() {
		var editorElement = CKEDITOR.document.getById( 'eticpurchaserulesEditor' );

		// :(((
		if ( isBBCodeBuiltIn ) {
			editorElement.setHtml(
				'Hello world!\n\n' +
				'I\'m an instance of [url=https://ckeditor.com]CKEditor[/url].'
			);
		}

		// Depending on the wysiwygarea plugin availability initialize classic or inline editor.
		if ( wysiwygareaAvailable ) {
//			CKEDITOR.replace( 'eticpurchaserulesEditor' );
			CKEDITOR.replace( 'eticpurchaserulesEditor', {
				removePlugins: 'image',
				extraPlugins: 'easyimage',
				cloudServices_tokenUrl: 'https://36762.cke-cs.com/token/dev/UM6e5GmaKWwu89mPGLfo7csdkFHnd6pThBTSG6KZ3PZIOnotQZZOpXzO92Lu',
				cloudServices_uploadUrl: 'https://36762.cke-cs.com/easyimage/upload/'
			} );
			
		} else {
			editorElement.setAttribute( 'contenteditable', 'true' );
			CKEDITOR.inline( 'eticpurchaserulesEditor' );

			// TODO we can consider displaying some info box that
			// without wysiwygarea the classic editor may not work.
		}
	};

	function isWysiwygareaAvailable() {
		// If in development mode, then the wysiwygarea must be available.
		// Split REV into two strings so builder does not replace it :D.
		if ( CKEDITOR.revision == ( '%RE' + 'V%' ) ) {
			return true;
		}

		return !!CKEDITOR.plugins.get( 'wysiwygarea' );
	}
} )();










// -----用票提醒-----ckEditor-----初始化-----
var initEticrulesEditor = ( function() {
	var wysiwygareaAvailable = isWysiwygareaAvailable(),
		isBBCodeBuiltIn = !!CKEDITOR.plugins.get( 'bbcode' );

	return function() {
		var editorElement = CKEDITOR.document.getById( 'eticrulesEditor' );

		// :(((
		if ( isBBCodeBuiltIn ) {
			editorElement.setHtml(
				'Hello world!\n\n' +
				'I\'m an instance of [url=https://ckeditor.com]CKEditor[/url].'
			);
		}

		// Depending on the wysiwygarea plugin availability initialize classic or inline editor.
		if ( wysiwygareaAvailable ) {
//			CKEDITOR.replace( 'eticrulesEditor' );
			CKEDITOR.replace( 'eticrulesEditor', {
				removePlugins: 'image',
				extraPlugins: 'easyimage',
				cloudServices_tokenUrl: 'https://36762.cke-cs.com/token/dev/UM6e5GmaKWwu89mPGLfo7csdkFHnd6pThBTSG6KZ3PZIOnotQZZOpXzO92Lu',
				cloudServices_uploadUrl: 'https://36762.cke-cs.com/easyimage/upload/'
			} );
		} else {
			editorElement.setAttribute( 'contenteditable', 'true' );
			CKEDITOR.inline( 'eticrulesEditor' );

			// TODO we can consider displaying some info box that
			// without wysiwygarea the classic editor may not work.
		}
	};

	function isWysiwygareaAvailable() {
		// If in development mode, then the wysiwygarea must be available.
		// Split REV into two strings so builder does not replace it :D.
		if ( CKEDITOR.revision == ( '%RE' + 'V%' ) ) {
			return true;
		}

		return !!CKEDITOR.plugins.get( 'wysiwygarea' );
	}
} )();










// -----退票說明-----ckEditor-----初始化-----
var initRefundrulesEditor = ( function() {
	var wysiwygareaAvailable = isWysiwygareaAvailable(),
		isBBCodeBuiltIn = !!CKEDITOR.plugins.get( 'bbcode' );

	return function() {
		var editorElement = CKEDITOR.document.getById( 'refundrulesEditor' );

		// :(((
		if ( isBBCodeBuiltIn ) {
			editorElement.setHtml(
				'Hello world!\n\n' +
				'I\'m an instance of [url=https://ckeditor.com]CKEditor[/url].'
			);
		}

		// Depending on the wysiwygarea plugin availability initialize classic or inline editor.
		if ( wysiwygareaAvailable ) {
//			CKEDITOR.replace( 'refundrulesEditor' );
			CKEDITOR.replace( 'refundrulesEditor', {
				removePlugins: 'image',
				extraPlugins: 'easyimage',
				cloudServices_tokenUrl: 'https://36762.cke-cs.com/token/dev/UM6e5GmaKWwu89mPGLfo7csdkFHnd6pThBTSG6KZ3PZIOnotQZZOpXzO92Lu',
				cloudServices_uploadUrl: 'https://36762.cke-cs.com/easyimage/upload/'
			} );
		} else {
			editorElement.setAttribute( 'contenteditable', 'true' );
			CKEDITOR.inline( 'refundrulesEditor' );

			// TODO we can consider displaying some info box that
			// without wysiwygarea the classic editor may not work.
		}
	};

	function isWysiwygareaAvailable() {
		// If in development mode, then the wysiwygarea must be available.
		// Split REV into two strings so builder does not replace it :D.
		if ( CKEDITOR.revision == ( '%RE' + 'V%' ) ) {
			return true;
		}

		return !!CKEDITOR.plugins.get( 'wysiwygarea' );
	}
} )();




