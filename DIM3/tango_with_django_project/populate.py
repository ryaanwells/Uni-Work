from rango.models import Category, Page
import os
os.environ['DJANGO_SETTINGS_MODULE'] = "myproject.settings"
c = Category(name='home')
c.save()
Page(category=c, title='Home - Property Search', url='http://www.home.co.uk', views=0).save()
Page(category=c, title='Right Move', url='http://www.rightmove.co.uk', views=0).save()
c = Category(name='sport')
c.save()
Page(category=c, title='BBC Sport', url='http://www.bbc.co.uk/sport/0/', views=0).save()
Page(category=c, title='Sky Sports', url='http://www.skysports.com/', views=0).save()
Page(category=c, title='Sports News', url='http://www.sport.co.uk/', views=0).save()
c = Category(name='fun')
c.save()
Page(category=c, title='The Fun Theory', url='http://www.thefuntheory.com/', views=0).save()
Page(category=c, title='Comp. Sci. for Fun', url='http://www.cs4fn.org/', views=0).save()
