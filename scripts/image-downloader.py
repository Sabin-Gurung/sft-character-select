import urllib.request
import re

def read_url(url):
    with urllib.request.urlopen(url) as response:
        html = response.read()
    return str(html)

def find_image_tags(text):
    matches = re.findall(r'<img src="[^"]*scale-to-width-down/310[^"]*"', text)
    return matches[0].split('"')[1].strip()

fighters = [
        "Ryu",  
        "E._Honda",  
        "Blanka",   
        "Guile",
        "Balrog",    
        "Ken",  
        "Chun-Li", 
        "Zangief", 
        "Dhalsim", 
        "Sagat"  ,
        "Vega", 
        "T._Hawk", 
        "Fei_Long",  
        "Dee_Jay",   
        "Cammy", 
        "M._Bison"
        ]

print(fighters)

baseurl = "https://streetfighter.fandom.com/wiki/"

for f in fighters:
    url = baseurl + f
    try:
        imgtag = find_image_tags(read_url(url))
        print("{}>{}".format(f, imgtag))

        # uncomment to donwload images
        # urllib.request.urlretrieve(imgtag, f.replace(".", "") + ".png")
        # print("Downloaded {} to {}.png".format(imgtag, f))
    except:
        print("failed {}".format(f))

# Ryu>https://static.wikia.nocookie.net/streetfighter/images/4/46/Ryurender.png/revision/latest/scale-to-width-down/310?cb=20170728171704
# E._Honda>https://static.wikia.nocookie.net/streetfighter/images/7/73/46NHkHz.png/revision/latest/scale-to-width-down/310?cb=20190909070353
# Blanka>https://static.wikia.nocookie.net/streetfighter/images/8/80/BLK_00.png/revision/latest/scale-to-width-down/310?cb=20180425080106
# Guile>https://static.wikia.nocookie.net/streetfighter/images/a/a2/Guilerender.png/revision/latest/scale-to-width-down/310?cb=20170728164132
# Balrog>https://static.wikia.nocookie.net/streetfighter/images/7/77/Balrogrender.png/revision/latest/scale-to-width-down/310?cb=20170728162928
# Ken>https://static.wikia.nocookie.net/streetfighter/images/b/b4/Kenrender.png/revision/latest/scale-to-width-down/310?cb=20170728171332
# Chun-Li>https://static.wikia.nocookie.net/streetfighter/images/e/e3/Chunrender.png/revision/latest/scale-to-width-down/310?cb=20170728163823
# Zangief>https://static.wikia.nocookie.net/streetfighter/images/8/88/Zangiefrender.png/revision/latest/scale-to-width-down/310?cb=20170728171808
# Dhalsim>https://static.wikia.nocookie.net/streetfighter/images/b/b1/Dhalsimrender.png/revision/latest/scale-to-width-down/310?cb=20170728164253
# Sagat>https://static.wikia.nocookie.net/streetfighter/images/6/69/Sagatclean.png/revision/latest/scale-to-width-down/310?cb=20180812160244
# Vega>https://static.wikia.nocookie.net/streetfighter/images/b/bf/Vegarender.png/revision/latest/scale-to-width-down/310?cb=20170728171753
# T._Hawk>https://static.wikia.nocookie.net/streetfighter/images/a/a4/SFVHawk.jpg/revision/latest/scale-to-width-down/310?cb=20160629141015
# Fei_Long>https://static.wikia.nocookie.net/streetfighter/images/e/e0/SFVFeiLong.jpg/revision/latest/scale-to-width-down/310?cb=20160629140902
# Cammy>https://static.wikia.nocookie.net/streetfighter/images/1/19/Cammyrender.png/revision/latest/scale-to-width-down/310?cb=20170728163129
# M._Bison>https://static.wikia.nocookie.net/streetfighter/images/1/17/Bisonrender.png/revision/latest/scale-to-width-down/310?cb=20170728171523
