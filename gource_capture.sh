gource --disable-progress --user-image-dir ./gource_avatars -1280x800 -s 2 --stop-at-end --output-framerate 60 --output-ppm-stream - | ffmpeg -y -r 60 -f image2pipe -vcodec ppm -i - -vcodec libx264 -crf 0 -vpre lossless_ultrafast -r 60 -threads 0 out.mkv

