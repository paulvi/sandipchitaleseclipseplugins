on run
	tell application "System Events"
		set frontmostAppPath to (path to frontmost application)
		repeat with theApplication in application processes
			if (file of theApplication is equal to frontmostAppPath) then
				set _frontMostApp to theApplication
			end if
		end repeat
		
		set _windowOne to window 1 of _frontMostApp
		
		set {_x, _y} to position of _windowOne
		set {_width, _height} to size of _windowOne
		
		try
			set newBounds to do shell script "java -XstartOnFirstThread -jar ~/Desktop/MoveResize.jar  " & _x & ":" & _y & ":" & _width & ":" & _height
			if (length of newBounds is greater than 0) then
				set newBounds to my split(newBounds, ":")
				
				set _x to item 1 of newBounds
				set _y to item 2 of newBounds
				set _width to item 3 of newBounds
				set _height to item 4 of newBounds
				
				set position of _windowOne to {_x, _y}
				set size of _windowOne to {_width, _height}
			end if
		end try
	end tell
end run

on split(someText, delimiter)
	set AppleScript's text item delimiters to delimiter
	set someText to someText's text items
	set AppleScript's text item delimiters to {""} --> restore delimiters to default value
	return someText
end split
