import numpy as np
import cv2

print "Digite o nome do arquivo de entrada (incluindo seu tipo, ex. .bmp etc.): "
arquivo = raw_input()

image = cv2.imread(arquivo)
height, width, channels = image.shape
manipulation_copy = image

row = height
colum = width

#RGB para Gray
hist = [None]*256
count = 0
while count < 256:
    hist[count] = 0.0
    count += 1

gray_level = 0

count = 0
while count < row:
    count_1 = 0
    while count_1 < colum:
        (b,g,r) = image[count,count_1]
        gray_level = 0.299*r + 0.587*g + 0.114*b
        hist[int(gray_level)] += 1
        manipulation_copy[count,count_1] = (gray_level,gray_level,gray_level)
        count_1 += 1
    count += 1

cv2.imwrite('Mars(gray).png',manipulation_copy)

#Equalizacao de histograma
total_px = row*colum
hist_f = [float(i) for i in hist]
    #PMF - Probability Mass Function
count = 0
while count < 256:
    hist_f[count] = hist_f[count]/total_px
    count +=1

    #CDF - Cumulative Distributive Function
count = 1
while count < 256:
    hist_f[count] = hist_f[count] + hist_f[count-1];
    count +=1

    #Multiplica a CDF pelo nivel de cinza maximo
count = 0
while count < 256:
    hist_f[count] = hist_f[count]*255
    count+=1

    #Integer histogram <- Float Histogram
count = 0
while count < 256:
    hist[count] = hist_f[count]
    count +=1

    #Aplica o novo histograma na imagem
count = 0
while count < row:
    count_1 = 0
    while count_1 <colum:
        (b,g,r) = manipulation_copy[count,count_1]
        gray_level = hist[b]
        manipulation_copy[count,count_1] = (gray_level,gray_level,gray_level)
        count_1 += 1
    count += 1

cv2.imwrite('Mars(gray-equalization).png',manipulation_copy)
   
#Procurando o caminho
    #distancia
destination_x = 1000
destination_y = 815
set_x = 415
set_y = 260
dm=0.0
steps=0
dist_n = [None]*8
indx = [None]*8
image[set_y,set_x] = (0,0,0)
image2 = cv2.imread(arquivo)

print "Calculando o caminho..."
while (1):
    count = 0
    while count<8:
        dist_n[count] = 0
        indx[count] = count
        count +=1
    
    #define a vizinhanca
    if(((set_x+1==destination_x)or(set_x-1==destination_x)or(set_x==destination_x))and((set_y+1==destination_y)or(set_y-1==destination_y)or(set_y==destination_y))):
        break

    neighborhood_x = (set_x+1,set_x+1,set_x+1,set_x,set_x-1,set_x-1,set_x-1,set_x)
    neighborhood_y = (set_y-1,set_y,set_y+1,set_y+1,set_y+1,set_y,set_y-1,set_y-1)

    dist_n[0] = (((set_x+1)-destination_x)**2+((set_y-1)-destination_y)**2)**(1/2.0)
    dist_n[1] = (((set_x+1)-destination_x)**2+((set_y)-destination_y)**2)**(1/2.0)
    dist_n[2] = (((set_x+1)-destination_x)**2+((set_y+1)-destination_y)**2)**(1/2.0)
    dist_n[3] = (((set_x)-destination_x)**2+((set_y+1)-destination_y)**2)**(1/2.0)
    dist_n[4] = (((set_x-1)-destination_x)**2+((set_y+1)-destination_y)**2)**(1/2.0)
    dist_n[5] = (((set_x-1)-destination_x)**2+((set_y)-destination_y)**2)**(1/2.0)
    dist_n[6] = (((set_x-1)-destination_x)**2+((set_y-1)-destination_y)**2)**(1/2.0)
    dist_n[7] = (((set_x)-destination_x)**2+((set_y-1)-destination_y)**2)**(1/2.0)
    
        #bubble sort
    permutation = 1
    aux = 0
    aux_indx = 0
    while permutation != 0:
        permutation = 0
        i = 0
        while i < 8-1:
            if(dist_n[i] > dist_n[i+1]):
               aux = dist_n[i+1]
               aux_indx = indx[i+1]
               dist_n[i+1] = dist_n[i]
               indx[i+1] = indx[i]
               dist_n[i] = aux
               indx[i] = aux_indx
               permutation = 1
            i +=1

    
    #escolhe o proximo pixel a ser analisado
    x=neighborhood_x[indx[0]]
    y=neighborhood_y[indx[0]]
    (b,g,r) = manipulation_copy[y,x]
    candidate_0 = b
    candidate_0x = x 
    candidate_0y = y

    x=neighborhood_x[indx[1]]
    y=neighborhood_y[indx[1]]
    (b,g,r) = manipulation_copy[y,x]                            
    candidate_1 = b
    candidate_1x = x 
    candidate_1y = y

    x=neighborhood_x[indx[2]]
    y=neighborhood_y[indx[2]]
    (b,g,r) = manipulation_copy[y,x]                            
    candidate_2 = b
    candidate_2x = x 
    candidate_2y = y

    if((candidate_0 < candidate_2 or candidate_0==candidate_2) and (candidate_0 < candidate_1 or candidate_0==candidate_1)):
        dm += (((set_x)-neighborhood_x[indx[0]])**2+((set_y)-neighborhood_y[indx[0]])**2)**(1/2.0)
        set_x = neighborhood_x[indx[0]]
        set_y = neighborhood_y[indx[0]]
    elif((candidate_1 < candidate_0 or candidate_1 == candidate_0) and (candidate_1 < candidate_2 or candidate_1==candidate_2)):
        dm += (((set_x)-neighborhood_x[indx[1]])**2+((set_y)-neighborhood_y[indx[1]])**2)**(1/2.0)
        set_x = neighborhood_x[indx[1]]
        set_y = neighborhood_y[indx[1]]
    elif((candidate_2 < candidate_1 or candidate_2==candidate_1) and (candidate_2 < candidate_0 or candidate_2==candidate_0)):
        dm += (((set_x)-neighborhood_x[indx[2]])**2+((set_y)-neighborhood_y[indx[2]])**2)**(1/2.0)
        set_x = neighborhood_x[indx[2]]
        set_y = neighborhood_y[indx[2]]
    steps+=1    
    image2[set_y,set_x]=(0,0,0)


dm += (((set_x)-destination_x)**2+((set_y)-destination_y)**2)**(1/2.0)
print "Qunatidade de passos dados: %d" % steps
print "Soma da distancia euclidiana entre cada pixel do caminho: %lf" % dm
image2[destination_y,destination_x] = (0,0,0)
image2 = cv2.resize(image2,(900,680))
cv2.imshow("Output", image2)
print "Aperte a tecla 's' para salvar a imagem com o caminho tracado"
key = cv2.waitKey(0) & 0xFF
if key == ord('s'):
    cv2.imwrite('PathDotted.png',image2)
    cv2.destroyAllWindows()
