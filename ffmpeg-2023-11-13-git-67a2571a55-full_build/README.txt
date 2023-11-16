FFmpeg 64-bit static Windows build from www.gyan.dev

Version: 2023-11-13-git-67a2571a55-full_build-www.gyan.dev

License: GPL v3

Source Code: https://github.com/FFmpeg/FFmpeg/commit/67a2571a55

External Assets
frei0r plugins:   https://www.gyan.dev/ffmpeg/builds/ffmpeg-frei0r-plugins
lensfun database: https://www.gyan.dev/ffmpeg/builds/ffmpeg-lensfun-db

git-full build configuration: 

ARCH                      x86 (generic)
big-endian                no
runtime cpu detection     yes
standalone assembly       yes
x86 assembler             nasm
MMX enabled               yes
MMXEXT enabled            yes
3DNow! enabled            yes
3DNow! extended enabled   yes
SSE enabled               yes
SSSE3 enabled             yes
AESNI enabled             yes
AVX enabled               yes
AVX2 enabled              yes
AVX-512 enabled           yes
AVX-512ICL enabled        yes
XOP enabled               yes
FMA3 enabled              yes
FMA4 enabled              yes
i686 features enabled     yes
CMOV is fast              yes
EBX available             yes
EBP available             yes
debug symbols             yes
strip symbols             yes
optimize for size         no
optimizations             yes
static                    yes
shared                    no
postprocessing support    yes
network support           yes
threading support         pthreads
safe bitstream reader     yes
texi2html enabled         no
perl enabled              yes
pod2man enabled           yes
makeinfo enabled          yes
makeinfo supports HTML    yes
xmllint enabled           yes

External libraries:
avisynth                libgsm                  libsvtav1
bzlib                   libharfbuzz             libtheora
chromaprint             libilbc                 libtwolame
frei0r                  libjxl                  libuavs3d
gmp                     liblensfun              libvidstab
gnutls                  libmodplug              libvmaf
iconv                   libmp3lame              libvo_amrwbenc
ladspa                  libmysofa               libvorbis
libaom                  libopencore_amrnb       libvpx
libaribb24              libopencore_amrwb       libwebp
libaribcaption          libopenjpeg             libx264
libass                  libopenmpt              libx265
libbluray               libopus                 libxavs2
libbs2b                 libplacebo              libxml2
libcaca                 librav1e                libxvid
libcdio                 librist                 libzimg
libcodec2               librubberband           libzmq
libdav1d                libshaderc              libzvbi
libdavs2                libshine                lzma
libflite                libsnappy               mediafoundation
libfontconfig           libsoxr                 sdl2
libfreetype             libspeex                zlib
libfribidi              libsrt
libgme                  libssh

External libraries providing hardware acceleration:
amf                     dxva2                   nvenc
cuda                    ffnvcodec               opencl
cuda_llvm               libmfx                  vulkan
cuvid                   libvpl
d3d11va                 nvdec

Libraries:
avcodec                 avformat                swresample
avdevice                avutil                  swscale
avfilter                postproc

Programs:
ffmpeg                  ffplay                  ffprobe

Enabled decoders:
aac                     ftr                     pcm_vidc
aac_fixed               g2m                     pcx
aac_latm                g723_1                  pdv
aasc                    g729                    pfm
ac3                     gdv                     pgm
ac3_fixed               gem                     pgmyuv
acelp_kelvin            gif                     pgssub
adpcm_4xm               gremlin_dpcm            pgx
adpcm_adx               gsm                     phm
adpcm_afc               gsm_ms                  photocd
adpcm_agm               h261                    pictor
adpcm_aica              h263                    pixlet
adpcm_argo              h263i                   pjs
adpcm_ct                h263p                   png
adpcm_dtk               h264                    ppm
adpcm_ea                h264_cuvid              prores
adpcm_ea_maxis_xa       h264_qsv                prosumer
adpcm_ea_r1             hap                     psd
adpcm_ea_r2             hca                     ptx
adpcm_ea_r3             hcom                    qcelp
adpcm_ea_xas            hdr                     qdm2
adpcm_g722              hevc                    qdmc
adpcm_g726              hevc_cuvid              qdraw
adpcm_g726le            hevc_qsv                qoi
adpcm_ima_acorn         hnm4_video              qpeg
adpcm_ima_alp           hq_hqa                  qtrle
adpcm_ima_amv           hqx                     r10k
adpcm_ima_apc           huffyuv                 r210
adpcm_ima_apm           hymt                    ra_144
adpcm_ima_cunning       iac                     ra_288
adpcm_ima_dat4          idcin                   ralf
adpcm_ima_dk3           idf                     rasc
adpcm_ima_dk4           iff_ilbm                rawvideo
adpcm_ima_ea_eacs       ilbc                    realtext
adpcm_ima_ea_sead       imc                     rka
adpcm_ima_iss           imm4                    rl2
adpcm_ima_moflex        imm5                    roq
adpcm_ima_mtf           indeo2                  roq_dpcm
adpcm_ima_oki           indeo3                  rpza
adpcm_ima_qt            indeo4                  rscc
adpcm_ima_rad           indeo5                  rtv1
adpcm_ima_smjpeg        interplay_acm           rv10
adpcm_ima_ssi           interplay_dpcm          rv20
adpcm_ima_wav           interplay_video         rv30
adpcm_ima_ws            ipu                     rv40
adpcm_ms                jacosub                 s302m
adpcm_mtaf              jpeg2000                sami
adpcm_psx               jpegls                  sanm
adpcm_sbpro_2           jv                      sbc
adpcm_sbpro_3           kgv1                    scpr
adpcm_sbpro_4           kmvc                    screenpresso
adpcm_swf               lagarith                sdx2_dpcm
adpcm_thp               lead                    sga
adpcm_thp_le            libaom_av1              sgi
adpcm_vima              libaribb24              sgirle
adpcm_xa                libaribcaption          sheervideo
adpcm_xmd               libcodec2               shorten
adpcm_yamaha            libdav1d                simbiosis_imx
adpcm_zork              libdavs2                sipr
agm                     libgsm                  siren
aic                     libgsm_ms               smackaud
alac                    libilbc                 smacker
alias_pix               libjxl                  smc
als                     libopencore_amrnb       smvjpeg
amrnb                   libopencore_amrwb       snow
amrwb                   libopus                 sol_dpcm
amv                     libspeex                sonic
anm                     libuavs3d               sp5x
ansi                    libvorbis               speedhq
anull                   libvpx_vp8              speex
apac                    libvpx_vp9              srgc
ape                     libzvbi_teletext        srt
apng                    loco                    ssa
aptx                    lscr                    stl
aptx_hd                 m101                    subrip
arbc                    mace3                   subviewer
argo                    mace6                   subviewer1
ass                     magicyuv                sunrast
asv1                    mdec                    svq1
asv2                    media100                svq3
atrac1                  metasound               tak
atrac3                  microdvd                targa
atrac3al                mimic                   targa_y216
atrac3p                 misc4                   tdsc
atrac3pal               mjpeg                   text
atrac9                  mjpeg_cuvid             theora
aura                    mjpeg_qsv               thp
aura2                   mjpegb                  tiertexseqvideo
av1                     mlp                     tiff
av1_cuvid               mmvideo                 tmv
av1_qsv                 mobiclip                truehd
avrn                    motionpixels            truemotion1
avrp                    movtext                 truemotion2
avs                     mp1                     truemotion2rt
avui                    mp1float                truespeech
ayuv                    mp2                     tscc
bethsoftvid             mp2float                tscc2
bfi                     mp3                     tta
bink                    mp3adu                  twinvq
binkaudio_dct           mp3adufloat             txd
binkaudio_rdft          mp3float                ulti
bintext                 mp3on4                  utvideo
bitpacked               mp3on4float             v210
bmp                     mpc7                    v210x
bmv_audio               mpc8                    v308
bmv_video               mpeg1_cuvid             v408
bonk                    mpeg1video              v410
brender_pix             mpeg2_cuvid             vb
c93                     mpeg2_qsv               vble
cavs                    mpeg2video              vbn
cbd2_dpcm               mpeg4                   vc1
ccaption                mpeg4_cuvid             vc1_cuvid
cdgraphics              mpegvideo               vc1_qsv
cdtoons                 mpl2                    vc1image
cdxl                    msa1                    vcr1
cfhd                    mscc                    vmdaudio
cinepak                 msmpeg4v1               vmdvideo
clearvideo              msmpeg4v2               vmix
cljr                    msmpeg4v3               vmnc
cllc                    msnsiren                vnull
comfortnoise            msp2                    vorbis
cook                    msrle                   vp3
cpia                    mss1                    vp4
cri                     mss2                    vp5
cscd                    msvideo1                vp6
cyuv                    mszh                    vp6a
dca                     mts2                    vp6f
dds                     mv30                    vp7
derf_dpcm               mvc1                    vp8
dfa                     mvc2                    vp8_cuvid
dfpwm                   mvdv                    vp8_qsv
dirac                   mvha                    vp9
dnxhd                   mwsc                    vp9_cuvid
dolby_e                 mxpeg                   vp9_qsv
dpx                     nellymoser              vplayer
dsd_lsbf                notchlc                 vqa
dsd_lsbf_planar         nuv                     vqc
dsd_msbf                on2avc                  wady_dpcm
dsd_msbf_planar         opus                    wavarc
dsicinaudio             osq                     wavpack
dsicinvideo             paf_audio               wbmp
dss_sp                  paf_video               wcmv
dst                     pam                     webp
dvaudio                 pbm                     webvtt
dvbsub                  pcm_alaw                wmalossless
dvdsub                  pcm_bluray              wmapro
dvvideo                 pcm_dvd                 wmav1
dxa                     pcm_f16le               wmav2
dxtory                  pcm_f24le               wmavoice
dxv                     pcm_f32be               wmv1
eac3                    pcm_f32le               wmv2
eacmv                   pcm_f64be               wmv3
eamad                   pcm_f64le               wmv3image
eatgq                   pcm_lxf                 wnv1
eatgv                   pcm_mulaw               wrapped_avframe
eatqi                   pcm_s16be               ws_snd1
eightbps                pcm_s16be_planar        xan_dpcm
eightsvx_exp            pcm_s16le               xan_wc3
eightsvx_fib            pcm_s16le_planar        xan_wc4
escape124               pcm_s24be               xbin
escape130               pcm_s24daud             xbm
evrc                    pcm_s24le               xface
exr                     pcm_s24le_planar        xl
fastaudio               pcm_s32be               xma1
ffv1                    pcm_s32le               xma2
ffvhuff                 pcm_s32le_planar        xpm
ffwavesynth             pcm_s64be               xsub
fic                     pcm_s64le               xwd
fits                    pcm_s8                  y41p
flac                    pcm_s8_planar           ylc
flashsv                 pcm_sga                 yop
flashsv2                pcm_u16be               yuv4
flic                    pcm_u16le               zero12v
flv                     pcm_u24be               zerocodec
fmvc                    pcm_u24le               zlib
fourxm                  pcm_u32be               zmbv
fraps                   pcm_u32le
frwu                    pcm_u8

Enabled encoders:
a64multi                huffyuv                 pcm_u16le
a64multi5               jpeg2000                pcm_u24be
aac                     jpegls                  pcm_u24le
aac_mf                  libaom_av1              pcm_u32be
ac3                     libcodec2               pcm_u32le
ac3_fixed               libgsm                  pcm_u8
ac3_mf                  libgsm_ms               pcm_vidc
adpcm_adx               libilbc                 pcx
adpcm_argo              libjxl                  pfm
adpcm_g722              libmp3lame              pgm
adpcm_g726              libopencore_amrnb       pgmyuv
adpcm_g726le            libopenjpeg             phm
adpcm_ima_alp           libopus                 png
adpcm_ima_amv           librav1e                ppm
adpcm_ima_apm           libshine                prores
adpcm_ima_qt            libspeex                prores_aw
adpcm_ima_ssi           libsvtav1               prores_ks
adpcm_ima_wav           libtheora               qoi
adpcm_ima_ws            libtwolame              qtrle
adpcm_ms                libvo_amrwbenc          r10k
adpcm_swf               libvorbis               r210
adpcm_yamaha            libvpx_vp8              ra_144
alac                    libvpx_vp9              rawvideo
alias_pix               libwebp                 roq
amv                     libwebp_anim            roq_dpcm
anull                   libx264                 rpza
apng                    libx264rgb              rv10
aptx                    libx265                 rv20
aptx_hd                 libxavs2                s302m
ass                     libxvid                 sbc
asv1                    ljpeg                   sgi
asv2                    magicyuv                smc
av1_amf                 mjpeg                   snow
av1_nvenc               mjpeg_qsv               sonic
av1_qsv                 mlp                     sonic_ls
avrp                    movtext                 speedhq
avui                    mp2                     srt
ayuv                    mp2fixed                ssa
bitpacked               mp3_mf                  subrip
bmp                     mpeg1video              sunrast
cfhd                    mpeg2_qsv               svq1
cinepak                 mpeg2video              targa
cljr                    mpeg4                   text
comfortnoise            msmpeg4v2               tiff
dca                     msmpeg4v3               truehd
dfpwm                   msrle                   tta
dnxhd                   msvideo1                ttml
dpx                     nellymoser              utvideo
dvbsub                  opus                    v210
dvdsub                  pam                     v308
dvvideo                 pbm                     v408
eac3                    pcm_alaw                v410
exr                     pcm_bluray              vbn
ffv1                    pcm_dvd                 vc2
ffvhuff                 pcm_f32be               vnull
fits                    pcm_f32le               vorbis
flac                    pcm_f64be               vp9_qsv
flashsv                 pcm_f64le               wavpack
flashsv2                pcm_mulaw               wbmp
flv                     pcm_s16be               webvtt
g723_1                  pcm_s16be_planar        wmav1
gif                     pcm_s16le               wmav2
h261                    pcm_s16le_planar        wmv1
h263                    pcm_s24be               wmv2
h263p                   pcm_s24daud             wrapped_avframe
h264_amf                pcm_s24le               xbm
h264_mf                 pcm_s24le_planar        xface
h264_nvenc              pcm_s32be               xsub
h264_qsv                pcm_s32le               xwd
hap                     pcm_s32le_planar        y41p
hdr                     pcm_s64be               yuv4
hevc_amf                pcm_s64le               zlib
hevc_mf                 pcm_s8                  zmbv
hevc_nvenc              pcm_s8_planar
hevc_qsv                pcm_u16be

Enabled hwaccels:
av1_d3d11va             hevc_dxva2              vc1_dxva2
av1_d3d11va2            hevc_nvdec              vc1_nvdec
av1_dxva2               hevc_vulkan             vp8_nvdec
av1_nvdec               mjpeg_nvdec             vp9_d3d11va
av1_vulkan              mpeg1_nvdec             vp9_d3d11va2
h264_d3d11va            mpeg2_d3d11va           vp9_dxva2
h264_d3d11va2           mpeg2_d3d11va2          vp9_nvdec
h264_dxva2              mpeg2_dxva2             wmv3_d3d11va
h264_nvdec              mpeg2_nvdec             wmv3_d3d11va2
h264_vulkan             mpeg4_nvdec             wmv3_dxva2
hevc_d3d11va            vc1_d3d11va             wmv3_nvdec
hevc_d3d11va2           vc1_d3d11va2

Enabled parsers:
aac                     dvdsub                  mpegaudio
aac_latm                evc                     mpegvideo
ac3                     flac                    opus
adx                     ftr                     png
amr                     g723_1                  pnm
av1                     g729                    qoi
avs2                    gif                     rv34
avs3                    gsm                     sbc
bmp                     h261                    sipr
cavsvideo               h263                    tak
cook                    h264                    vc1
cri                     hdr                     vorbis
dca                     hevc                    vp3
dirac                   ipu                     vp8
dnxhd                   jpeg2000                vp9
dolby_e                 jpegxl                  vvc
dpx                     misc4                   webp
dvaudio                 mjpeg                   xbm
dvbsub                  mlp                     xma
dvd_nav                 mpeg4video              xwd

Enabled demuxers:
aa                      idf                     pcm_f64le
aac                     iff                     pcm_mulaw
aax                     ifv                     pcm_s16be
ac3                     ilbc                    pcm_s16le
ac4                     image2                  pcm_s24be
ace                     image2_alias_pix        pcm_s24le
acm                     image2_brender_pix      pcm_s32be
act                     image2pipe              pcm_s32le
adf                     image_bmp_pipe          pcm_s8
adp                     image_cri_pipe          pcm_u16be
ads                     image_dds_pipe          pcm_u16le
adx                     image_dpx_pipe          pcm_u24be
aea                     image_exr_pipe          pcm_u24le
afc                     image_gem_pipe          pcm_u32be
aiff                    image_gif_pipe          pcm_u32le
aix                     image_hdr_pipe          pcm_u8
alp                     image_j2k_pipe          pcm_vidc
amr                     image_jpeg_pipe         pdv
amrnb                   image_jpegls_pipe       pjs
amrwb                   image_jpegxl_pipe       pmp
anm                     image_pam_pipe          pp_bnk
apac                    image_pbm_pipe          pva
apc                     image_pcx_pipe          pvf
ape                     image_pfm_pipe          qcp
apm                     image_pgm_pipe          r3d
apng                    image_pgmyuv_pipe       rawvideo
aptx                    image_pgx_pipe          realtext
aptx_hd                 image_phm_pipe          redspark
aqtitle                 image_photocd_pipe      rka
argo_asf                image_pictor_pipe       rl2
argo_brp                image_png_pipe          rm
argo_cvg                image_ppm_pipe          roq
asf                     image_psd_pipe          rpl
asf_o                   image_qdraw_pipe        rsd
ass                     image_qoi_pipe          rso
ast                     image_sgi_pipe          rtp
au                      image_sunrast_pipe      rtsp
av1                     image_svg_pipe          s337m
avi                     image_tiff_pipe         sami
avisynth                image_vbn_pipe          sap
avr                     image_webp_pipe         sbc
avs                     image_xbm_pipe          sbg
avs2                    image_xpm_pipe          scc
avs3                    image_xwd_pipe          scd
bethsoftvid             imf                     sdns
bfi                     ingenient               sdp
bfstm                   ipmovie                 sdr2
bink                    ipu                     sds
binka                   ircam                   sdx
bintext                 iss                     segafilm
bit                     iv8                     ser
bitpacked               ivf                     sga
bmv                     ivr                     shorten
boa                     jacosub                 siff
bonk                    jpegxl_anim             simbiosis_imx
brstm                   jv                      sln
c93                     kux                     smacker
caf                     kvag                    smjpeg
cavsvideo               laf                     smush
cdg                     libgme                  sol
cdxl                    libmodplug              sox
cine                    libopenmpt              spdif
codec2                  live_flv                srt
codec2raw               lmlm4                   stl
concat                  loas                    str
dash                    lrc                     subviewer
data                    luodat                  subviewer1
daud                    lvf                     sup
dcstr                   lxf                     svag
derf                    m4v                     svs
dfa                     matroska                swf
dfpwm                   mca                     tak
dhav                    mcc                     tedcaptions
dirac                   mgsts                   thp
dnxhd                   microdvd                threedostr
dsf                     mjpeg                   tiertexseq
dsicin                  mjpeg_2000              tmv
dss                     mlp                     truehd
dts                     mlv                     tta
dtshd                   mm                      tty
dv                      mmf                     txd
dvbsub                  mods                    ty
dvbtxt                  moflex                  usm
dxa                     mov                     v210
ea                      mp3                     v210x
ea_cdata                mpc                     vag
eac3                    mpc8                    vc1
epaf                    mpegps                  vc1t
evc                     mpegts                  vividas
ffmetadata              mpegtsraw               vivo
filmstrip               mpegvideo               vmd
fits                    mpjpeg                  vobsub
flac                    mpl2                    voc
flic                    mpsub                   vpk
flv                     msf                     vplayer
fourxm                  msnwc_tcp               vqf
frm                     msp                     vvc
fsb                     mtaf                    w64
fwse                    mtv                     wady
g722                    musx                    wav
g723_1                  mv                      wavarc
g726                    mvi                     wc3
g726le                  mxf                     webm_dash_manifest
g729                    mxg                     webvtt
gdv                     nc                      wsaud
genh                    nistsphere              wsd
gif                     nsp                     wsvqa
gsm                     nsv                     wtv
gxf                     nut                     wv
h261                    nuv                     wve
h263                    obu                     xa
h264                    ogg                     xbin
hca                     oma                     xmd
hcom                    osq                     xmv
hevc                    paf                     xvag
hls                     pcm_alaw                xwma
hnm                     pcm_f32be               yop
ico                     pcm_f32le               yuv4mpegpipe
idcin                   pcm_f64be

Enabled muxers:
a64                     h261                    pcm_s24be
ac3                     h263                    pcm_s24le
ac4                     h264                    pcm_s32be
adts                    hash                    pcm_s32le
adx                     hds                     pcm_s8
aiff                    hevc                    pcm_u16be
alp                     hls                     pcm_u16le
amr                     ico                     pcm_u24be
amv                     ilbc                    pcm_u24le
apm                     image2                  pcm_u32be
apng                    image2pipe              pcm_u32le
aptx                    ipod                    pcm_u8
aptx_hd                 ircam                   pcm_vidc
argo_asf                ismv                    psp
argo_cvg                ivf                     rawvideo
asf                     jacosub                 rm
asf_stream              kvag                    roq
ass                     latm                    rso
ast                     lrc                     rtp
au                      m4v                     rtp_mpegts
avi                     matroska                rtsp
avif                    matroska_audio          sap
avm2                    md5                     sbc
avs2                    microdvd                scc
avs3                    mjpeg                   segafilm
bit                     mkvtimestamp_v2         segment
caf                     mlp                     smjpeg
cavsvideo               mmf                     smoothstreaming
chromaprint             mov                     sox
codec2                  mp2                     spdif
codec2raw               mp3                     spx
crc                     mp4                     srt
dash                    mpeg1system             stream_segment
data                    mpeg1vcd                streamhash
daud                    mpeg1video              sup
dfpwm                   mpeg2dvd                swf
dirac                   mpeg2svcd               tee
dnxhd                   mpeg2video              tg2
dts                     mpeg2vob                tgp
dv                      mpegts                  truehd
eac3                    mpjpeg                  tta
evc                     mxf                     ttml
f4v                     mxf_d10                 uncodedframecrc
ffmetadata              mxf_opatom              vc1
fifo                    null                    vc1t
fifo_test               nut                     voc
filmstrip               obu                     vvc
fits                    oga                     w64
flac                    ogg                     wav
flv                     ogv                     webm
framecrc                oma                     webm_chunk
framehash               opus                    webm_dash_manifest
framemd5                pcm_alaw                webp
g722                    pcm_f32be               webvtt
g723_1                  pcm_f32le               wsaud
g726                    pcm_f64be               wtv
g726le                  pcm_f64le               wv
gif                     pcm_mulaw               yuv4mpegpipe
gsm                     pcm_s16be
gxf                     pcm_s16le

Enabled protocols:
async                   http                    rtmp
bluray                  httpproxy               rtmpe
cache                   https                   rtmps
concat                  icecast                 rtmpt
concatf                 ipfs_gateway            rtmpte
crypto                  ipns_gateway            rtmpts
data                    librist                 rtp
fd                      libsrt                  srtp
ffrtmpcrypt             libssh                  subfile
ffrtmphttp              libzmq                  tcp
file                    md5                     tee
ftp                     mmsh                    tls
gopher                  mmst                    udp
gophers                 pipe                    udplite
hls                     prompeg

Enabled filters:
a3dscope                datascope               pad
abench                  dblur                   pad_opencl
abitscope               dcshift                 pal100bars
acompressor             dctdnoiz                pal75bars
acontrast               ddagrab                 palettegen
acopy                   deband                  paletteuse
acrossfade              deblock                 pan
acrossover              decimate                perms
acrusher                deconvolve              perspective
acue                    dedot                   phase
addroi                  deesser                 photosensitivity
adeclick                deflate                 pixdesctest
adeclip                 deflicker               pixelize
adecorrelate            deinterlace_qsv         pixscope
adelay                  dejudder                pp
adenorm                 delogo                  pp7
aderivative             derain                  premultiply
adrawgraph              deshake                 prewitt
adrc                    deshake_opencl          prewitt_opencl
adynamicequalizer       despill                 program_opencl
adynamicsmooth          detelecine              pseudocolor
aecho                   dialoguenhance          psnr
aemphasis               dilation                pullup
aeval                   dilation_opencl         qp
aevalsrc                displace                random
aexciter                dnn_classify            readeia608
afade                   dnn_detect              readvitc
afdelaysrc              dnn_processing          realtime
afftdn                  doubleweave             remap
afftfilt                drawbox                 remap_opencl
afifo                   drawgraph               removegrain
afir                    drawgrid                removelogo
afireqsrc               drawtext                repeatfields
afirsrc                 drmeter                 replaygain
aformat                 dynaudnorm              reverse
afreqshift              earwax                  rgbashift
afwtdn                  ebur128                 rgbtestsrc
agate                   edgedetect              roberts
agraphmonitor           elbg                    roberts_opencl
ahistogram              entropy                 rotate
aiir                    epx                     rubberband
aintegral               eq                      sab
ainterleave             equalizer               scale
alatency                erosion                 scale2ref
alimiter                erosion_opencl          scale_cuda
allpass                 estdif                  scale_qsv
allrgb                  exposure                scale_vulkan
allyuv                  extractplanes           scdet
aloop                   extrastereo             scharr
alphaextract            fade                    scroll
alphamerge              feedback                segment
amerge                  fftdnoiz                select
ametadata               fftfilt                 selectivecolor
amix                    field                   sendcmd
amovie                  fieldhint               separatefields
amplify                 fieldmatch              setdar
amultiply               fieldorder              setfield
anequalizer             fifo                    setparams
anlmdn                  fillborders             setpts
anlmf                   find_rect               setrange
anlms                   firequalizer            setsar
anoisesrc               flanger                 settb
anull                   flip_vulkan             shear
anullsink               flite                   showcqt
anullsrc                floodfill               showcwt
apad                    format                  showfreqs
aperms                  fps                     showinfo
aphasemeter             framepack               showpalette
aphaser                 framerate               showspatial
aphaseshift             framestep               showspectrum
apsnr                   freezedetect            showspectrumpic
apsyclip                freezeframes            showvolume
apulsator               frei0r                  showwaves
arealtime               frei0r_src              showwavespic
aresample               fspp                    shuffleframes
areverse                gblur                   shufflepixels
arls                    gblur_vulkan            shuffleplanes
arnndn                  geq                     sidechaincompress
asdr                    gradfun                 sidechaingate
asegment                gradients               sidedata
aselect                 graphmonitor            sierpinski
asendcmd                grayworld               signalstats
asetnsamples            greyedge                signature
asetpts                 guided                  silencedetect
asetrate                haas                    silenceremove
asettb                  haldclut                sinc
ashowinfo               haldclutsrc             sine
asidedata               hdcd                    siti
asisdr                  headphone               smartblur
asoftclip               hflip                   smptebars
aspectralstats          hflip_vulkan            smptehdbars
asplit                  highpass                sobel
ass                     highshelf               sobel_opencl
astats                  hilbert                 sofalizer
astreamselect           histeq                  spectrumsynth
asubboost               histogram               speechnorm
asubcut                 hqdn3d                  split
asupercut               hqx                     spp
asuperpass              hstack                  sr
asuperstop              hstack_qsv              ssim
atadenoise              hsvhold                 ssim360
atempo                  hsvkey                  stereo3d
atilt                   hue                     stereotools
atrim                   huesaturation           stereowiden
avectorscope            hwdownload              streamselect
avgblur                 hwmap                   subtitles
avgblur_opencl          hwupload                super2xsai
avgblur_vulkan          hwupload_cuda           superequalizer
avsynctest              hysteresis              surround
axcorrelate             identity                swaprect
azmq                    idet                    swapuv
backgroundkey           il                      tblend
bandpass                inflate                 telecine
bandreject              interlace               testsrc
bass                    interleave              testsrc2
bbox                    join                    thistogram
bench                   kerndeint               threshold
bilateral               kirsch                  thumbnail
bilateral_cuda          ladspa                  thumbnail_cuda
biquad                  lagfun                  tile
bitplanenoise           latency                 tiltshelf
blackdetect             lenscorrection          tinterlace
blackframe              lensfun                 tlut2
blend                   libplacebo              tmedian
blend_vulkan            libvmaf                 tmidequalizer
blockdetect             life                    tmix
blurdetect              limitdiff               tonemap
bm3d                    limiter                 tonemap_opencl
boxblur                 loop                    tpad
boxblur_opencl          loudnorm                transpose
bs2b                    lowpass                 transpose_opencl
bwdif                   lowshelf                transpose_vulkan
bwdif_cuda              lumakey                 treble
bwdif_vulkan            lut                     tremolo
cas                     lut1d                   trim
ccrepack                lut2                    unpremultiply
cellauto                lut3d                   unsharp
channelmap              lutrgb                  unsharp_opencl
channelsplit            lutyuv                  untile
chorus                  mandelbrot              uspp
chromaber_vulkan        maskedclamp             v360
chromahold              maskedmax               vaguedenoiser
chromakey               maskedmerge             varblur
chromakey_cuda          maskedmin               vectorscope
chromanr                maskedthreshold         vflip
chromashift             maskfun                 vflip_vulkan
ciescope                mcdeint                 vfrdet
codecview               mcompand                vibrance
color                   median                  vibrato
color_vulkan            mergeplanes             vidstabdetect
colorbalance            mestimate               vidstabtransform
colorchannelmixer       metadata                vif
colorchart              midequalizer            vignette
colorcontrast           minterpolate            virtualbass
colorcorrect            mix                     vmafmotion
colorhold               monochrome              volume
colorize                morpho                  volumedetect
colorkey                movie                   vpp_qsv
colorkey_opencl         mpdecimate              vstack
colorlevels             mptestsrc               vstack_qsv
colormap                msad                    w3fdif
colormatrix             multiply                waveform
colorspace              negate                  weave
colorspace_cuda         nlmeans                 xbr
colorspectrum           nlmeans_opencl          xcorrelate
colortemperature        nlmeans_vulkan          xfade
compand                 nnedi                   xfade_opencl
compensationdelay       noformat                xfade_vulkan
concat                  noise                   xmedian
convolution             normalize               xstack
convolution_opencl      null                    xstack_qsv
convolve                nullsink                yadif
copy                    nullsrc                 yadif_cuda
corr                    openclsrc               yaepblur
cover_rect              oscilloscope            yuvtestsrc
crop                    overlay                 zmq
cropdetect              overlay_cuda            zoneplate
crossfeed               overlay_opencl          zoompan
crystalizer             overlay_qsv             zscale
cue                     overlay_vulkan
curves                  owdenoise

Enabled bsfs:
aac_adtstoasc           h264_redundant_pps      pcm_rechunk
av1_frame_merge         hapqa_extract           pgs_frame_merge
av1_frame_split         hevc_metadata           prores_metadata
av1_metadata            hevc_mp4toannexb        remove_extradata
chomp                   imx_dump_header         setts
dca_core                media100_to_mjpegb      text2movsub
dts2pts                 mjpeg2jpeg              trace_headers
dump_extradata          mjpega_dump_header      truehd_core
dv_error_marker         mov2textsub             vp9_metadata
eac3_core               mp3_header_decompress   vp9_raw_reorder
evc_frame_merge         mpeg2_metadata          vp9_superframe
extract_extradata       mpeg4_unpack_bframes    vp9_superframe_split
filter_units            noise                   vvc_metadata
h264_metadata           null                    vvc_mp4toannexb
h264_mp4toannexb        opus_metadata

Enabled indevs:
dshow                   lavfi                   vfwcap
gdigrab                 libcdio

Enabled outdevs:
caca                    sdl2

git-full external libraries' versions: 

AMF v1.4.30-2-g2f32635
aom v3.7.0-650-g32461d2a21
aribb24 v1.0.3-5-g5e9be27
aribcaption 1.1.1
AviSynthPlus v3.7.3-26-gd2b7c666
bs2b 3.1.0
chromaprint 1.5.1
codec2 1.2.0-19-g4f4f7304
dav1d 1.3.0-12-ge47a39c
davs2 1.7-1-gb41cf11
ffnvcodec n12.1.14.0-1-g75f032b
flite v2.2-55-g6c9f20d
freetype VER-2-13-2
frei0r v2.3.1
fribidi v1.0.13
gsm 1.0.22
harfbuzz 8.3.0
ladspa-sdk 1.17
lame 3.100
lensfun v0.3.95-1341-g542b9894
libass 0.17.0-61-g9f4e6af
libcdio-paranoia 10.2
libgme 0.6.3
libilbc v3.0.4-346-g6adb26d4a4
libjxl v0.9-snapshot-625-gd3a69dbe
libopencore-amrnb 0.1.6
libopencore-amrwb 0.1.6
libplacebo v6.338.0-53-gc02c40cc
libsoxr 0.1.3
libssh 0.10.4
libtheora 1.1.1
libwebp v1.3.2-103-g24d7f9cb
oneVPL 2.9
OpenCL-Headers v2023.04.17-9-g9ce9a72
openmpt libopenmpt-0.6.12-37-g6d0bfe47f
opus v1.4-9-gc8549975
rav1e p20231024-17-g5555b5fd
rist 0.2.8
rubberband v1.8.1
SDL release-2.28.0-242-g63e9827b9
shaderc v2023.7-1-g0d6f72f
shine 3.1.1
snappy 1.1.10
speex Speex-1.2.1-20-g3693431
srt v1.5.3-17-g4a8067c
SVT-AV1 v1.7.0-4-gba13fac2
twolame 0.4.0
uavs3d v1.1-47-g1fd0491
vidstab v1.1.1-4-g05829db
vmaf v2.3.1-172-gc97a97e6
vo-amrwbenc 0.1.3
vorbis v1.3.7-10-g84c02369
vpx v1.13.1-528-gb7d847d0e
vulkan-loader v1.3.270
x264 v0.164.3161
x265 3.5-151-g8ee01d45b
xavs2 1.4
xvid v1.3.7
zeromq 4.3.5
zimg release-3.0.5-150-g7143181
zvbi v0.2.42

