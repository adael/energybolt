package music;

public class Music {
	// TODO: Lot to do
}

/*
 * 
 * NOTAS DE INTERES.
 * 
 * @SEE http://forum.java.sun.com/thread.jspa?threadID=625292&messageID=3564537
 * 
 * This can be simplified if you aren't going to use the same structure: 1. Add
 * a name to the enum Sound say NEW_SOUND 2. Add a line to associate the enum
 * with a sound’s file name soundNames.put(NEW_SOUND,"MyNewSound.wav"); 3. Make
 * a new SoundMap private final static SoundMap soundMap = new SoundMap(); 4.
 * Start a the SoundMap thread to load the file soundMap.start(); 5. Call
 * Media.play(NEW_SOUND); //kind of obfuscated behind other function calls
 * 
 * you could replace Media.Play(NEW_SOUND) with this:
 * 
 * final Clip clip = soundMap.getSound(NEW_SOUND);
 * setVolume(clip,Media.getVolume()); clip.setFramePosition(0); clip.start();
 * 
 * 
 * 
 * You could extract everything you need from this sequence including setting
 * the volume:
 * 
 * try { final SoundMap soundMap = new SoundMap(); soundMap.start();
 * soundMap.join(); final Clip clip = soundMap.getSound(SHIPDIE); if
 * (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) { float value =
 * (float) (50.0 / 100.0); FloatControl gainControl = (FloatControl)
 * clip.getControl(FloatControl.Type.MASTER_GAIN); float dB = (float)
 * (Math.log(value == 0.0 ? 0.0001 : value) / Math.log(10.0) 20.0);
 * gainControl.setValue(dB); } clip.setFramePosition(0); clip.start(); } catch
 * (InterruptedException interruptedException) {
 * interruptedException.printStackTrace(); }
 * 
 * 
 * 
 * I tried the code out to make sure it works. Please note this was designed
 * using threads so that I could load all of the sounds, images, text files at
 * once without proceeding until they were all loaded (it prevented a lot of
 * bugs). If you don't have the .join() call then you'll get an exception
 * because the resource didn't finish loading. This method was also chosen so
 * that the files wouldn't have to be loaded every time they were used. I admit
 * it can be kind of difficult working backwards out of someone else's code, but
 * it can often bring you to a new understanding on how to do things.
 * 
 * Good luck with your game.
 */