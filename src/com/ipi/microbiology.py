import numpy as np
import cv2

print "Digite o nome do arquivo de entrada (incluindo seu tipo, ex. .tif etc.): "
arquivo = raw_input()

image = cv2.imread(arquivo)
height, width, channels = image.shape

row = height
colum = width

#verificando se a imagem só apresenta dois tons de cinza
count = 0
while count < row:
    count_1= 0
    while count_1 < colum:
        (b,g,r)=image[count, count_1] #[linha,coluna]
        if(b>127):
            image[count,count_1]=(255,255,255)
        else:
            image[count,count_1]=(0,0,0)
        count_1+=1
    count+=1

print "Contando componentes conectados..."

matrix_image=np.zeros((row,colum),dtype=np.int64)

fila_x = [None]*(row*colum)
fila_y = [None]*(row*colum)

count_obj=0
count=0
count_fila=0
while count<row:#percorre cada pixel da imagem
    count_1=0
    while count_1<colum:
        (b,g,r)=image[count,count_1]
        if(b==0 and matrix_image[count,count_1]==0):
            matrix_image[count,count_1]=1
            i=count
            j=count_1
            stop = 0
            while(stop==0):
                if(count < row-1):#verifica se o vizinho de baixo existe
                    (b,g,r)=image[count+1,count_1]            
                    if((b==0) and (matrix_image[count+1,count_1]==0)):
                        matrix_image[count+1,count_1]=1
                        fila_x[count_fila]=count+1
                        fila_y[count_fila]=count_1
                        count_fila+=1
                if count>0:#verifica se o vizinho da cima existe
                    (b,g,r)=image[count-1,count_1]
                    if (b==0 and matrix_image[count-1,count_1]==0):
                       matrix_image[count-1,count_1]=1
                       fila_x[count_fila]=count-1
                       fila_y[count_fila]=count_1
                       count_fila+=1                
                if(count_1 < colum-1):#verifica se o vizinho da direita exite
                    (b,g,r)=image[count,count_1+1]
                    if (b==0 and matrix_image[count,count_1+1]==0):
                        matrix_image[count,count_1+1]=1
                        fila_x[count_fila]=count
                        fila_y[count_fila]=count_1+1
                        count_fila+=1            
                if(count_1>0):#verifica se o vizinho esquerda existe
                    (b,g,r)=image[count,count_1-1]
                    if (b==0 and matrix_image[count,count_1-1]==0):
                        matrix_image[count,count_1-1]=1
                        fila_x[count_fila]=count
                        fila_y[count_fila]=count_1-1
                        count_fila+=1
                if (count>0 and count_1>0): #verifica se o vizinho a esquerda e acima existe
                    (b,g,r)=image[count-1,count_1-1]
                    if(b==0 and matrix_image[count-1,count_1-1]==0):
                        matrix_image[count-1,count_1-1]=1
                        fila_x[count_fila]=count-1
                        fila_y[count_fila]=count_1-1
                        count_fila+=1
                if(count < colum-1 and count_1>0):#verifica se o vizinho a esqueda e abaixo existe
                    (b,g,r)=image[count+1,count_1-1]
                    if(b==0 and matrix_image[count+1,count_1-1]==0):
                        matrix_image[count+1,count_1-1]=1
                        fila_x[count_fila]=count+1
                        fila_y[count_fila]=count_1-1
                        count_fila+=1
                if (count>0 and count_1<row-1):#verifica se o vizinho a direita e acima existe
                    (b,g,r)=image[count-1,count_1+1]
                    if(b==0 and matrix_image[count-1,count_1+1]==0):
                        matrix_image[count-1,count_1+1]=1
                        fila_x[count_fila]=count-1
                        fila_y[count_fila]=count_1+1
                        count_fila+=1
                if(count < colum -1 and count_1 < row-1):#verifica se o vizinho a direita e abaixo existe
                    (b,g,r)=image[count+1,count_1+1]
                    if(b==0 and matrix_image[count+1,count_1+1]==0):
                       matrix_image[count+1,count_1+1]=1
                       fila_x[count_fila]=count+1
                       fila_y[count_fila]=count_1+1
                       count_fila+=1
                if(count_fila>0):
                    #print "acessa ultimo endereco da fila"
                    count_fila = count_fila-1
                    count=fila_x[count_fila]
                    count_1=fila_y[count_fila]
                else:
                    stop=1
            count_obj+=1
            count = i
            count_1 = j 
        count_1+=1
    count+=1

print "Quantidade de componentes conectados encontrados: %d\n" % count_obj

#Inverte as cores da imagem
print "Contando componentes com buracos..."
count = 0
while count < row:
    count_1= 0
    while count_1 < colum:
        (b,g,r)=image[count, count_1] #[linha,coluna]
        if(b>127):
            image[count,count_1]=(0,0,0)
        else:
            image[count,count_1]=(255,255,255)
        count_1+=1
    count+=1

matrix_image=np.zeros((row,colum),dtype=np.int64)

fila_x = [None]*(row*colum)
fila_y = [None]*(row*colum)

count_obj=0
count=0
count_fila=0
while count<row:
    count_1=0
    while count_1<colum:
        (b,g,r)=image[count,count_1]
        if(b==0 and matrix_image[count,count_1]==0):
            matrix_image[count,count_1]=1
            i=count
            j=count_1
            stop = 0
            while(stop==0):
                if(count < row-1):#verifica se o vizinho de baixo existe
                    (b,g,r)=image[count+1,count_1]            
                    if((b==0) and (matrix_image[count+1,count_1]==0)):
                        matrix_image[count+1,count_1]=1
                        fila_x[count_fila]=count+1
                        fila_y[count_fila]=count_1
                        count_fila+=1
                if count>0:#verifica se o vizinho da cima existe
                    (b,g,r)=image[count-1,count_1]
                    if (b==0 and matrix_image[count-1,count_1]==0):
                       matrix_image[count-1,count_1]=1
                       fila_x[count_fila]=count-1
                       fila_y[count_fila]=count_1
                       count_fila+=1                
                if(count_1 < colum-1):#verifica se o vizinho da direita exite
                    (b,g,r)=image[count,count_1+1]
                    if (b==0 and matrix_image[count,count_1+1]==0):
                        matrix_image[count,count_1+1]=1
                        fila_x[count_fila]=count
                        fila_y[count_fila]=count_1+1
                        count_fila+=1            
                if(count_1>0):#verifica se o vizinho esquerda existe
                    (b,g,r)=image[count,count_1-1]
                    if (b==0 and matrix_image[count,count_1-1]==0):
                        matrix_image[count,count_1-1]=1
                        fila_x[count_fila]=count
                        fila_y[count_fila]=count_1-1
                        count_fila+=1
                if (count>0 and count_1>0): #verifica se o vizinho a esquerda e acima existe
                    (b,g,r)=image[count-1,count_1-1]
                    if(b==0 and matrix_image[count-1,count_1-1]==0):
                        matrix_image[count-1,count_1-1]=1
                        fila_x[count_fila]=count-1
                        fila_y[count_fila]=count_1-1
                        count_fila+=1
                if(count < colum-1 and count_1>0):#verifica se o vizinho a esqueda e abaixo existe
                    (b,g,r)=image[count+1,count_1-1]
                    if(b==0 and matrix_image[count+1,count_1-1]==0):
                        matrix_image[count+1,count_1-1]=1
                        fila_x[count_fila]=count+1
                        fila_y[count_fila]=count_1-1
                        count_fila+=1
                if (count>0 and count_1<row-1):#verifica se o vizinho a direita e acima existe
                    (b,g,r)=image[count-1,count_1+1]
                    if(b==0 and matrix_image[count-1,count_1+1]==0):
                        matrix_image[count-1,count_1+1]=1
                        fila_x[count_fila]=count-1
                        fila_y[count_fila]=count_1+1
                        count_fila+=1
                if(count < colum -1 and count_1 < row-1):#verifica se o vizinho a direita e abaixo existe
                    (b,g,r)=image[count+1,count_1+1]
                    if(b==0 and matrix_image[count+1,count_1+1]==0):
                       matrix_image[count+1,count_1+1]=1
                       fila_x[count_fila]=count+1
                       fila_y[count_fila]=count_1+1
                       count_fila+=1
                if(count_fila>0):
                    #print "acessa ultimo endereco da fila"
                    count_fila = count_fila-1
                    count=fila_x[count_fila]
                    count_1=fila_y[count_fila]
                else:
                    stop=1
            count_obj+=1
            count = i
            count_1 = j 
        count_1+=1
    count+=1

print "No total, existe(m) %d buraco(s) dentro de componentes conectados\n" % (count_obj-1) #o algoritmo conta o fundo preto da iamgem negativa, por isso decrementa-se 1
print "*** OBS.: O algoritmo analisa vizinhanca de 8, mas considera componentes ou buracos formado por 1 pixel"
